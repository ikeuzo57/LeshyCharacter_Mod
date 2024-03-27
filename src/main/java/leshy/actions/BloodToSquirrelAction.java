package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.LeshyMod;
import leshy.cards.Starvation;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.orbs.CreatureOrb;

public class BloodToSquirrelAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public BloodToSquirrelAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractOrb o : p.orbs){
                if(o instanceof CreatureOrb && !(((CreatureOrb) o).creatureCard instanceof Starvation) && ((CreatureOrb) o).creatureCard.costType == AbstractCreatureCard.CreatureCostType.BLOOD){
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
                if(card.costType == AbstractCreatureCard.CreatureCostType.BLOOD)
                    for(int i=0; i<card.extraCost; i++)
                        addToBot(new MakeTempCardInHandAction(LeshyMod.getSquirrel()));
                isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 1, false, "Choose a creature to sacrifice.");
            tickDuration();

            return;

        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {

            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            if(card instanceof AbstractCreatureCard){
                addToTop(new CreatureSacrificeAction(((AbstractCreatureCard) card).orb));
                if(((AbstractCreatureCard) card).costType == AbstractCreatureCard.CreatureCostType.BLOOD)
                    for(int i=0; i<((AbstractCreatureCard) card).extraCost; i++)
                        addToBot(new MakeTempCardInHandAction(LeshyMod.getSquirrel()));
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.isDone = true;

        }

        tickDuration();

    }

}













