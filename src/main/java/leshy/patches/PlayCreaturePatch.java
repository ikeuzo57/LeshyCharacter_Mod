package leshy.patches;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import leshy.actions.SummonCreatureAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.orbs.CreatureOrb;

public class PlayCreaturePatch {

    @SpirePatch(clz = UseCardAction.class, method = "<ctor>", paramtypez = {AbstractCard.class, AbstractCreature.class})
    public static class PutInStasis {
        @SpirePostfixPatch
        public static void awayItGoes(UseCardAction __instance, AbstractCard card, AbstractCreature target) {
            if(card instanceof AbstractCreatureCard){

                CreatureOrb orb = new CreatureOrb( (AbstractCreatureCard) card, AbstractDungeon.player.hand);
                if(((AbstractCreatureCard) card).current.contains(AbstractCreatureCard.Sigils.GUARDIAN)) {
                    AbstractDungeon.actionManager.addToBottom(new SummonCreatureAction(orb, 0));
                }else {
                    AbstractDungeon.actionManager.addToBottom(new SummonCreatureAction(orb));
                }

            }
        }
    }

    @SpirePatch(clz = UseCardAction.class, method = "update")
    public static class DontMoveCreatureCards {

        @SpireInsertPatch(locator = Locator.class, localvars = {"targetCard", "duration"})
        public static SpireReturn<?> SelfStasis(UseCardAction __instance, AbstractCard targetCard, @ByRef float[] duration) {
            if(targetCard instanceof AbstractCreatureCard){

                if (targetCard.purgeOnUse) {
                    AbstractDungeon.actionManager.addToTop(new ShowCardAndPoofAction(targetCard));
                }

                AbstractDungeon.player.cardInUse = null;
                targetCard.exhaustOnUseOnce = false;
                targetCard.dontTriggerOnUseCard = false;
                AbstractDungeon.actionManager.addToBottom(new HandCheckAction());

                duration[0] = duration[0] - Gdx.graphics.getDeltaTime();

                if (duration[0] < 0.0F) {
                    __instance.isDone = true;
                }

                return SpireReturn.Return(null);

            }else{
                return SpireReturn.Continue();
            }
        }

        private static class Locator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "purgeOnUse");
                return LineFinder.findInOrder(ctMethodToPatch, fieldAccessMatcher);
            }
        }

    }


}
