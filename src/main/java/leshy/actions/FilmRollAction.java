package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import leshy.cards.FilmRoll;
import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.ArrayList;

public class FilmRollAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    ArrayList<AbstractCreatureCard> list;

    public FilmRollAction(){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    
    @Override
    public void update() {

        if(this.duration == Settings.ACTION_DUR_MED) {

            list = new ArrayList<>();
            for (AbstractCard c : p.masterDeck.group) {
                if (c instanceof AbstractCreatureCard) {
                    list.add((AbstractCreatureCard) c.makeSameInstanceOf());
                }
            }
            if (list.size() < 3) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, FilmRoll.cardStrings.EXTENDED_DESCRIPTION[0], true));
                this.isDone = true;
                return;
            }

            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            int size = list.size() / 3;

            while (temp.size() < size) {
                int index = AbstractDungeon.cardRandomRng.random(0, list.size() - 1);
                temp.addToTop(list.get(index));
                list.remove(index);
            }

            while(temp.size() > 3){
                temp.removeTopCard();
            }


            AbstractDungeon.gridSelectScreen.open(temp, 1, FilmRoll.cardStrings.EXTENDED_DESCRIPTION[1], false);

            tickDuration();

            return;

        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            if(AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof AbstractCreatureCard){
                addToTop(new FilmRollStatAction(list, (AbstractCreatureCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0)));
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }

            this.isDone = true;

        }

        tickDuration();

    }

}













