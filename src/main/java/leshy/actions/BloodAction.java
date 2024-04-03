package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.LeshyMod;
import leshy.cards.TrialOfBlood;
import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.ArrayList;

public class BloodAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public BloodAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            ArrayList<AbstractCard> deck = AbstractDungeon.player.drawPile.group;

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            int i = deck.size()-1;
            while(i>=0 && i>deck.size()-4){
                tmp.addToTop(deck.get(i).makeSameInstanceOf());
                i--;
            }

            if(tmp.isEmpty()){
                this.isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 0, true, TrialOfBlood.cardStrings.EXTENDED_DESCRIPTION[0]);

            int totalBloodCost = 0;
            for(AbstractCard c : tmp.group){
                if(c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).costType == AbstractCreatureCard.CreatureCostType.BLOOD){
                    totalBloodCost += ((AbstractCreatureCard) c).extraCost;
                }
            }

            if(totalBloodCost >= 4){
                LeshyMod.logger.info("Total Blood Cost : " + totalBloodCost);
                addToBot(new BloodCreatureAction(totalBloodCost));
            }

            this.isDone = true;
            return;

        }

        tickDuration();

    }

}













