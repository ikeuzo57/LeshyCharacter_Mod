package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.cards.GoFish;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.orbs.CreatureOrb;

public class GoFishAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public GoFishAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            int num = 0;
            for(AbstractOrb o : p.orbs){
                if(o instanceof CreatureOrb){
                    AbstractCreatureCard card = (AbstractCreatureCard) ((CreatureOrb) o).creatureCard.makeSameInstanceOf();
                    card.orb = (CreatureOrb) o;
                    card.applyPowers();
                    tmp.addToBottom(card);
                    num++;
                }
            }
            if(num == 0){
                isDone = true;
                return;
            }
            if(num == 1){
                ((AbstractCreatureCard) tmp.getTopCard()).orb.creatureCard.bounce = true;
                addToTop(new CreatureSacrificeAction(((AbstractCreatureCard) tmp.getTopCard()).orb));
                isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 1, GoFish.cardStrings.EXTENDED_DESCRIPTION[0], false);
            tickDuration();

            return;

        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {

            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            c.applyPowers();
            c.unhover();

            if(c instanceof AbstractCreatureCard) {

                ((AbstractCreatureCard) c).orb.creatureCard.bounce = true;
                addToTop(new CreatureSacrificeAction(((AbstractCreatureCard) c).orb));

            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.isDone = true;

        }

        tickDuration();

    }

}













