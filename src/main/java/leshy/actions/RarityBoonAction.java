package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.cards.abstracts.BoonCard;
import leshy.cards.*;

import java.util.ArrayList;


public class RarityBoonAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public RarityBoonAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {



            ArrayList<AbstractCard> tmp = new ArrayList<>();

            tmp.add(new BoonOfGoatsBlood());
            tmp.add(new BoonOfTheAmbidextrous());
            tmp.add(new BoonOfTheBoneLord());
            tmp.add(new BoonOfTheForest());
            tmp.add(new BoonOfTheMagpiesEye());

            while(tmp.size() > 3){
                tmp.remove(AbstractDungeon.miscRng.random(tmp.size()-1));
            }

            AbstractDungeon.cardRewardScreen.customCombatOpen(tmp, TrialOfRarity.cardStrings.EXTENDED_DESCRIPTION[0], false);
            tickDuration();

            return;

        }

        if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {

            AbstractCard card = AbstractDungeon.cardRewardScreen.discoveryCard;

            if(card instanceof BoonCard)
                ((BoonCard) card).applyBoon();

            AbstractDungeon.cardRewardScreen.discoveryCard = null;

            this.isDone = true;

        }

        tickDuration();

    }

}













