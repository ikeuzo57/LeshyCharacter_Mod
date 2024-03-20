package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.cards.*;

public class WoodcarverAction extends AbstractGameAction {

    public WoodcarverAction(){
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            CardGroup tmp = getTotemOptions();

            if(tmp.isEmpty()){
                this.isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 1, "Pick a totem head.", false);

            tickDuration();
            return;

        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
            AbstractDungeon.player.masterDeck.addToTop(card);

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.isDone = true;
            return;

        }
        tickDuration();

    }

    private static CardGroup getTotemOptions() {
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AvianTotem avian = new AvianTotem();
        CanineTotem canine = new CanineTotem();
        HoovedTotem hooved = new HoovedTotem();
        InsectTotem insect = new InsectTotem();
        ReptileTotem reptile = new ReptileTotem();
        tmp.addToTop(avian);
        tmp.addToTop(canine);
        tmp.addToTop(hooved);
        tmp.addToTop(insect);
        tmp.addToTop(reptile);

        for(AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if(c instanceof AvianTotem)
                tmp.removeCard(avian);
            if(c instanceof CanineTotem)
                tmp.removeCard(canine);
            if(c instanceof HoovedTotem)
                tmp.removeCard(hooved);
            if(c instanceof InsectTotem)
                tmp.removeCard(insect);
            if(c instanceof ReptileTotem)
                tmp.removeCard(reptile);
        }
        return tmp;
    }

}













