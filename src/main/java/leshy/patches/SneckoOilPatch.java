package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.RandomizeHandCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.Glitch;


public class SneckoOilPatch {

    @SpirePatch(clz = RandomizeHandCostAction.class, method = "update", paramtypez = {})
    public static class ShopCreatureCards{

        @SpireInsertPatch(locator = SneckoOilPatch.Locator.class, localvars = {"card"})
        public static void OrbDamage(RandomizeHandCostAction __instance,  @ByRef AbstractCard[] card) {

            if(card[0] instanceof AbstractCreatureCard || card[0] instanceof Glitch){

                int newCost = AbstractDungeon.cardRandomRng.random(3);
                AbstractCreatureCard.CreatureCostType newType;

                if(newCost == 0){
                    newType = AbstractCreatureCard.CreatureCostType.NONE;
                }else{
                    int rngCostType = AbstractDungeon.cardRandomRng.random(1);
                    if(rngCostType == 0){
                        newType = AbstractCreatureCard.CreatureCostType.BLOOD;
                    }else{
                        newType = AbstractCreatureCard.CreatureCostType.BONE;
                        newCost *= 2;
                    }
                }

                if(card[0] instanceof AbstractCreatureCard){
                    if (((AbstractCreatureCard) card[0]).extraCost != newCost || ((AbstractCreatureCard) card[0]).costType != newType){
                        card[0].isCostModified = true;
                        ((AbstractCreatureCard) card[0]).extraCost = newCost;
                        ((AbstractCreatureCard) card[0]).costType = newType;
                        card[0].applyPowers();
                    }
                }

            }

        }

    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher methodCallMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "hand");

            int[] line = LineFinder.findInOrder(ctBehavior, methodCallMatcher);

            line[0] += 1;

            return line;
        }
    }

}
