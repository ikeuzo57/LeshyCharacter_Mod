package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import leshy.cards.abstracts.AbstractCreatureCard;

public class BottledOpossumPatch {


    @SpirePatch(clz = CardGroup.class, method = "initializeDeck", paramtypez = {CardGroup.class})
    public static class MoveToTop {

        @SpireInsertPatch(locator = Locator.class, localvars = {"c"})
        public static void Insert(CardGroup __instance, CardGroup masterDeck, @ByRef AbstractCard[] c) {

            if(c[0] instanceof AbstractCreatureCard && ((AbstractCreatureCard) c[0]).bottledOpossum){
                c[0].inBottleFlame = true;
            }

        }

        private static class Locator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "inBottleFlame");
                return LineFinder.findInOrder(ctMethodToPatch, fieldAccessMatcher);
            }
        }

    }


    @SpirePatch(clz = CardGroup.class, method = "initializeDeck", paramtypez = {CardGroup.class})
    public static class RemoveInFlame {

        @SpirePostfixPatch
        public static void Postfix(CardGroup __instance, CardGroup masterDeck) {

            for(AbstractCard c : AbstractDungeon.player.drawPile.group){
                if(c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).bottledOpossum){
                    c.inBottleFlame = false;
                }
            }

        }


    }


}
