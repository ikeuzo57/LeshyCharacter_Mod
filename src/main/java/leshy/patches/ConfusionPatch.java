package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.Glitch;

@SpirePatch(clz = ConfusionPower.class, method = "onCardDraw")
public class ConfusionPatch {

    @SpirePostfixPatch
    public static void Postfix(ConfusionPower obj, @ByRef AbstractCard[] card) {

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
