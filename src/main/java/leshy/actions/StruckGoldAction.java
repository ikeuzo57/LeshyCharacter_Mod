package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.cards.Starvation;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.GoldNugget;
import leshy.orbs.CreatureOrb;

public class StruckGoldAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public StruckGoldAction(){
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
                GoldNugget gold = new GoldNugget();
                gold.baseHealth = card.orb.evokeAmount;
                addToBot(new SummonCreatureAction(new CreatureOrb(gold)));
                isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 4, true, "Where be da gold?");
            tickDuration();

            return;

        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {

            int totalHealth = 0;

            for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                if(c instanceof AbstractCreatureCard) {
                    totalHealth += ((AbstractCreatureCard) c).orb.evokeAmount;
                    addToTop(new CreatureSacrificeAction(((AbstractCreatureCard) c).orb));
                }
            }

            GoldNugget gold = new GoldNugget();
            gold.baseHealth = totalHealth;
            addToBot(new SummonCreatureAction(new CreatureOrb(gold)));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.isDone = true;

        }

        tickDuration();

    }

}













