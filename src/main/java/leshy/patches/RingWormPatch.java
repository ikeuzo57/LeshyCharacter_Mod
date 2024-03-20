package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import javassist.CtBehavior;
import leshy.cards.RingWorm;
import leshy.relics.DeadSurvivorsRelic;


public class RingWormPatch {

    @SpirePatch(clz = CampfireSmithEffect.class, method = "update")
    public static class AddKeys {
        @SpireInsertPatch(locator = RingWormPatch.Locator.class, localvars = {"c"})
        public static void patch(CampfireSmithEffect __instance, AbstractCard c) {

            if(c instanceof RingWorm){
                AbstractDungeon.player.masterDeck.removeCard(c);
                boolean killedSurvivors = false;
                for(AbstractRelic r : AbstractDungeon.player.relics)
                    if(r instanceof DeadSurvivorsRelic){
                        killedSurvivors = true;
                        break;
                    }

                if(!killedSurvivors){
                    DeadSurvivorsRelic relic = new DeadSurvivorsRelic();
                    relic.instantObtain();
                }
            }

        }
    }

    public static class Locator extends SpireInsertLocator{
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");
            return LineFinder.findInOrder(ctBehavior, methodCallMatcher);
        }
    }

}
