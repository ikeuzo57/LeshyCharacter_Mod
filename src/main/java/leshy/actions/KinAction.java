package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.cards.TrialOfBlood;

import java.util.ArrayList;

public class KinAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public KinAction(){
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
                AbstractCard c = deck.get(i).makeSameInstanceOf();
                c.applyPowers();
                tmp.addToTop(c);
                i--;
            }

            if(tmp.isEmpty()){
                this.isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 0, true, TrialOfBlood.cardStrings.EXTENDED_DESCRIPTION[0]);

            addToBot(new KinCreatureAction());

            this.isDone = true;
            return;

        }

        tickDuration();

    }

}













