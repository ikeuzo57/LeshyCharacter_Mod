package leshy.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.map.RoomTypeAssigner;
import com.megacrit.cardcrawl.rooms.*;
import javassist.CtBehavior;
import leshy.LeshyMod;
import leshy.characters.Leshy;
import leshy.rooms.DredgingRoom;
import leshy.rooms.LeshyEventRoom;
import leshy.rooms.MycologistsRoom;
import leshy.rooms.MysteriousStonesRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class LeshyEventPatch {

    @SpirePatch(clz = AbstractDungeon.class, method = "generateRoomTypes", paramtypez = {ArrayList.class, int.class})
    public static class AddKeys {

        @SpirePrefixPatch
        public static void PreFix(ArrayList<AbstractRoom> roomList, @ByRef int[] count){

            if(AbstractDungeon.player instanceof Leshy){
                ReflectionHacks.setPrivateStatic(AbstractDungeon.class, "eventRoomChance", 0F);
                //ReflectionHacks.setPrivateStatic(AbstractDungeon.class, "restRoomChance", 0.08F);

                boolean place = true;
                for (MapRoomNode n : AbstractDungeon.map.get(7))
                    if (n.hasEdges() && n.getRoom() == null){
                        if(place){
                            n.setRoom(new DredgingRoom());
                            count[0] -= 1;
                        }
                        place = !place;
                    }

            }

        }

        @SpirePostfixPatch
        public static void PostFix(ArrayList<AbstractRoom> roomList, int availableRoomCount){

            if(AbstractDungeon.player instanceof Leshy){

                float sigilRoomChance = 0.12F;
                int sigilCount = Math.round(availableRoomCount * sigilRoomChance);
                LeshyMod.logger.info(" SIGIL (" + toPercentage(sigilRoomChance) + "): " + sigilCount);
                for (int i = 0; i < sigilCount; i++)
                    roomList.add(new MysteriousStonesRoom());

                /*
                float shroomRoomChance = 0.08F;
                int shroomCount = Math.round(availableRoomCount * shroomRoomChance);
                LeshyMod.logger.info(" SHROOM (" + toPercentage(shroomRoomChance) + "): " + shroomCount);
                for (int i = 0; i < shroomCount; i++)
                    roomList.add(new MycologistsRoom());
                */

                float leshyEventRoomChance = 0.12F;
                int leshyEventCount = Math.round(availableRoomCount * leshyEventRoomChance);
                LeshyMod.logger.info(" LESHY_EVENT (" + toPercentage(leshyEventRoomChance) + "): " + leshyEventCount);
                for (int i = 0; i < leshyEventCount; i++)
                    roomList.add(new LeshyEventRoom());

            }

        }

    }

    @SpirePatch(clz = RoomTypeAssigner.class, method = "ruleAssignableToRow", paramtypez = {MapRoomNode.class, AbstractRoom.class})
    public static class RowRestrict {

        @SpireInsertPatch(locator = Locator.class, localvars = {"applicableRooms"})
        public static void Insert(MapRoomNode n, AbstractRoom roomToBeSet, @ByRef List<Class<? extends AbstractRoom>>[] applicableRooms){

            List<Class<? extends AbstractRoom>> newApplicableRooms = Arrays.asList((Class<? extends AbstractRoom>[])new Class[] { RestRoom.class, MonsterRoomElite.class });
            applicableRooms[0] = newApplicableRooms;

        }


        private static class Locator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(Collections.class, "singletonList");
                return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
            }
        }

    }

    @SpirePatch(clz = RoomTypeAssigner.class, method = "ruleParentMatches", paramtypez = {ArrayList.class, AbstractRoom.class})
    public static class ParentRestrict {

        @SpireInsertPatch(locator = Locator.class, localvars = {"applicableRooms"})
        public static void Insert(ArrayList<MapRoomNode> parents, AbstractRoom roomToBeSet, @ByRef List<Class<? extends AbstractRoom>>[] applicableRooms){

            List<Class<? extends AbstractRoom>> newApplicableRooms = Arrays.asList((Class<? extends AbstractRoom>[])new Class[] { RestRoom.class, TreasureRoom.class, ShopRoom.class, MonsterRoomElite.class, MysteriousStonesRoom.class});
            applicableRooms[0] = newApplicableRooms;

        }


        private static class Locator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(Arrays.class, "asList");
                int[] line = LineFinder.findInOrder(ctBehavior, methodCallMatcher);

                line[0] += 1;

                return line;
            }
        }

    }

    @SpirePatch(clz = RoomTypeAssigner.class, method = "ruleSiblingMatches", paramtypez = {ArrayList.class, AbstractRoom.class})
    public static class SiblingRestrict {

        @SpireInsertPatch(locator = Locator.class, localvars = {"applicableRooms"})
        public static void Insert(ArrayList<MapRoomNode> siblings, AbstractRoom roomToBeSet, @ByRef List<Class<? extends AbstractRoom>>[] applicableRooms){

            List<Class<? extends AbstractRoom>> newApplicableRooms = Arrays.asList((Class<? extends AbstractRoom>[])new Class[] { RestRoom.class, MonsterRoom.class, EventRoom.class, MonsterRoomElite.class, ShopRoom.class, LeshyEventRoom.class, MysteriousStonesRoom.class});
            applicableRooms[0] = newApplicableRooms;

        }


        private static class Locator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(Arrays.class, "asList");
                int[] line = LineFinder.findInOrder(ctBehavior, methodCallMatcher);

                line[0] += 1;

                return line;
            }
        }

    }

    @SpirePatch(clz = AbstractPlayer.class, method = "render")
    public static class removePlayer {
        @SpirePrefixPatch
        public static SpireReturn patch(){
            if(AbstractDungeon.getCurrRoom() instanceof MycologistsRoom || AbstractDungeon.getCurrRoom() instanceof MysteriousStonesRoom
                    || AbstractDungeon.getCurrRoom() instanceof LeshyEventRoom || AbstractDungeon.getCurrRoom() instanceof DredgingRoom)
                return SpireReturn.Return();
            return SpireReturn.Continue();
        }
    }


    public static String toPercentage(float n) {
        return String.format("%.0f", new Object[] { Float.valueOf(n * 100.0F) }) + "%";
    }

}
