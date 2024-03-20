package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractWisdomCard;
import leshy.orbs.CreatureOrb;

public class WisdomCreatureAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    AbstractWisdomCard wc;

    public WisdomCreatureAction(AbstractWisdomCard wc){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.wc = wc;
    }

    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for(AbstractOrb o : p.orbs){
                if(o instanceof CreatureOrb){
                    AbstractCreatureCard card = (AbstractCreatureCard) ((CreatureOrb) o).creatureCard.makeSameInstanceOf();
                    card.orb = (CreatureOrb) o;
                    card.applyPowers();
                    tmp.addToBottom(card);
                }
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 1, "Choose a creature to the give the sigil.", false);
            tickDuration();

            return;

        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            c.applyPowers();
            c.unhover();

            if(c instanceof AbstractCreatureCard) {

                this.wc.applySigil(((AbstractCreatureCard) c).orb.creatureCard);

            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.isDone = true;

        }

        tickDuration();

    }

}













