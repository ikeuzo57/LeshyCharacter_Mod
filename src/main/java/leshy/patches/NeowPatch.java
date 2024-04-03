package leshy.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble;
import javassist.CtBehavior;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractPeltCard;
import leshy.cards.RingWorm;
import leshy.characters.Leshy;

import java.util.ArrayList;



public class NeowPatch {

    public static final String ID = LeshyMod.makeID("NeowExtension");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    @SpirePatch(clz = NeowEvent.class, method = "buttonEffect", paramtypez = {int.class})
    public static class ButtonEffect {

        @SpireInsertPatch(locator = ButtonEffect.Locator.class)
        public static void Insert(NeowEvent __instance, int buttonPressed) {

            if(AbstractDungeon.player instanceof Leshy){
                dismissBubble();
                AbstractDungeon.effectList.add(new InfiniteSpeechBubble(1100.0F * Settings.xScale, AbstractDungeon.floorY + 60.0F * Settings.yScale, DESCRIPTIONS[0]));
                __instance.roomEventText.updateDialogOption(0, OPTIONS[0]);
            }

        }

        public static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(RoomEventDialog.class, "clearRemainingOptions");
                return new int[]{LineFinder.findAllInOrder(ctBehavior, new ArrayList(), methodCallMatcher)[1]};
            }
        }

        @SpirePrefixPatch
        public static SpireReturn Prefix(NeowEvent __instance, int buttonPressed) {

            if (AbstractDungeon.player instanceof Leshy && (int) ReflectionHacks.getPrivate(__instance, NeowEvent.class, "screenNum") == 99) {

                dismissBubble();
                AbstractDungeon.effectList.add(new InfiniteSpeechBubble(1100.0F * Settings.xScale, AbstractDungeon.floorY + 60.0F * Settings.yScale, NeowEvent.TEXT[8]));

                ArrayList<AbstractCreatureCard> common = new ArrayList<>();
                for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group)
                    if (c instanceof AbstractCreatureCard && !(c instanceof AbstractPeltCard)){
                        common.add((AbstractCreatureCard) c.makeCopy());
                        common.add((AbstractCreatureCard) c.makeCopy());
                    }

                ArrayList<AbstractCreatureCard> uncommon = new ArrayList<>();
                for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group)
                    if (c instanceof AbstractCreatureCard && !(c instanceof AbstractPeltCard)){
                        uncommon.add((AbstractCreatureCard) c.makeCopy());
                        if(!(c instanceof RingWorm))
                            uncommon.add((AbstractCreatureCard) c.makeCopy());
                    }

                CardGroup sealedGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                for (int i = 0; i < 10; i++)
                    sealedGroup.addToTop(common.remove(AbstractDungeon.cardRandomRng.random(common.size() - 1)));

                for (int i = 0; i < 5; i++)
                    sealedGroup.addToTop(uncommon.remove(AbstractDungeon.cardRandomRng.random(uncommon.size() - 1)));

                sealedGroup.sortAlphabetically(true);
                sealedGroup.sortByRarity(true);

                ReflectionHacks.setPrivate(__instance, NeowEvent.class, "pickCard", true);
                AbstractDungeon.gridSelectScreen.open(sealedGroup, 4, OPTIONS[1], false);

                __instance.roomEventText.clearRemainingOptions();
                __instance.roomEventText.updateDialogOption(0, NeowEvent.OPTIONS[3]);
                ReflectionHacks.setPrivate(__instance, NeowEvent.class, "screenNum", 100);

                return SpireReturn.Return();
            }
            return SpireReturn.Continue();

        }


    }

    @SpirePatch(clz = NeowEvent.class, method = "<ctor>", paramtypez = {boolean.class})
    public static class NeowLoad{

        @SpirePostfixPatch
        public static void Postfix(NeowEvent __instance, boolean isDone){

            if(!(Settings.isEndless && AbstractDungeon.floorNum > 1) && !shouldSkipNeowDialog() && isDone && AbstractDungeon.player instanceof Leshy){
                dismissBubble();
                AbstractDungeon.effectList.add(new InfiniteSpeechBubble(1100.0F * Settings.xScale, AbstractDungeon.floorY + 60.0F * Settings.yScale, DESCRIPTIONS[0]));
                __instance.roomEventText.updateDialogOption(0, OPTIONS[0]);
            }

        }



    }


    public static boolean shouldSkipNeowDialog() {
        if (Settings.seedSet && !Settings.isTrial && !Settings.isDailyRun)
            return false;
        return !Settings.isStandardRun();
    }


    public static void dismissBubble() {
        for (AbstractGameEffect e : AbstractDungeon.effectList) {
            if (e instanceof InfiniteSpeechBubble)
                ((InfiniteSpeechBubble) e).dismiss();
        }
    }


}




