package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.cards.GoldNugget;
import leshy.cards.Starvation;
import leshy.cards.StruckGold;
import leshy.cards.TrialOfWisdom;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.orbs.CreatureOrb;

public class HammerAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public HammerAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractOrb o : p.orbs){
                if(o instanceof CreatureOrb && !(((CreatureOrb) o).creatureCard instanceof Starvation)){
                    AbstractCreatureCard card = (AbstractCreatureCard) ((CreatureOrb) o).creatureCard.makeSameInstanceOf();
                    card.orb = (CreatureOrb) o;
                    card.applyPowers();
                    tmp.addToBottom(card);
                }
            }
            if(tmp.isEmpty()){
                isDone = true;
                return;
            }
            if(tmp.size() == 1){
                AbstractCreatureCard card = (AbstractCreatureCard) tmp.getTopCard();
                addToTop(new CreatureSacrificeAction(card.orb));
                addToTop(new DrawCardAction(1));
                isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 1, AbstractCreatureCard.SCREEN_DESCRIPTION[1], false);
            tickDuration();

            return;

        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {

            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            if(card instanceof AbstractCreatureCard)
                addToTop(new CreatureSacrificeAction(((AbstractCreatureCard)card).orb));

            addToTop(new DrawCardAction(1));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.isDone = true;

        }

        tickDuration();

    }

}













