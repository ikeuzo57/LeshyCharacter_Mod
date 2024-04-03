package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.cards.FilmRoll;
import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.ArrayList;

public class FilmRollStatAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    ArrayList<AbstractCreatureCard> list;

    AbstractCreatureCard cost;

    public FilmRollStatAction(ArrayList<AbstractCreatureCard> list, AbstractCreatureCard cost){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.list = list;
        this.cost = cost;
    }

    @Override
    public void update() {

        if(this.duration == Settings.ACTION_DUR_MED) {

            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            int size = list.size() / 2;

            while (temp.size() < size) {
                int index = AbstractDungeon.cardRandomRng.random(0, list.size() - 1);
                temp.addToTop(list.get(index));
                list.remove(index);
            }

            while(temp.size() > 3){
                temp.removeTopCard();
            }

            AbstractDungeon.gridSelectScreen.open(temp, 1, FilmRoll.cardStrings.EXTENDED_DESCRIPTION[3], false);
            tickDuration();

            return;

        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {

            if(AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof AbstractCreatureCard){
                addToTop(new FilmRollSigilsAction(list, cost, (AbstractCreatureCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0)));
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }

            this.isDone = true;

        }

        tickDuration();

    }

}













