package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.actions.BloodCostAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.powers.BonePower;



@SpirePatch(clz = AbstractPlayer.class, method = "useCard", paramtypez = {AbstractCard.class, AbstractMonster.class, int.class})
public class CreatureCostPatch {

    @SpirePostfixPatch
    public static void PostFix(AbstractPlayer __instance, AbstractCard card, AbstractMonster target, int energyOnUse) {

        if(!card.isInAutoplay && card instanceof AbstractCreatureCard){

            if(((AbstractCreatureCard) card).costType == AbstractCreatureCard.CreatureCostType.BLOOD){

                AbstractDungeon.actionManager.addToTop(new BloodCostAction(((AbstractCreatureCard) card).extraCost, card));

            }else if(((AbstractCreatureCard) card).costType == AbstractCreatureCard.CreatureCostType.BONE){

                for(AbstractPower ap : AbstractDungeon.player.powers){
                    if(ap instanceof BonePower){
                        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, ap, ((AbstractCreatureCard) card).extraCost));
                    }
                }

            }

        }

    }

}


