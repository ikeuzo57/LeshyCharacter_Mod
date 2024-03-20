package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.DeathCard;

import java.util.ArrayList;

public class FilmRollSigilsAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    ArrayList<AbstractCreatureCard> list;

    AbstractCreatureCard cost;
    AbstractCreatureCard stats;
    AbstractCreatureCard sigils;

    public FilmRollSigilsAction(ArrayList<AbstractCreatureCard> list, AbstractCreatureCard cost, AbstractCreatureCard stats){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.list = list;
        this.cost = cost;
        this.stats = stats;
    }

    @Override
    public void update() {

        if(this.duration == Settings.ACTION_DUR_MED) {

            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c : list){
                temp.addToTop(c);
            }

            while(temp.size() > 3){
                temp.removeTopCard();
            }

            AbstractDungeon.gridSelectScreen.open(temp, 1, "Select the Sigils", false);
            tickDuration();

            return;

        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            if(AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof AbstractCreatureCard){
                sigils = (AbstractCreatureCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            }

            if(cost != null && stats != null && sigils != null){

                DeathCard deathCard = new DeathCard();

                deathCard.cost = cost.cost;
                deathCard.costType = cost.costType;
                deathCard.extraCost = cost.extraCost;

                deathCard.baseAttack = stats.baseAttack;
                deathCard.baseHealth = stats.baseHealth;

                deathCard.gained.addAll(sigils.innate);
                deathCard.gained.addAll(sigils.gained);
                deathCard.current.addAll(deathCard.gained);

                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(deathCard));

            }else{
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, "Something went wrong", true));
            }

            this.isDone = true;

        }

        tickDuration();

    }

}













