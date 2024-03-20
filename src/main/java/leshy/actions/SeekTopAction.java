package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import leshy.LeshyMod;

public class SeekTopAction extends AbstractGameAction {

    private AbstractPlayer p;

    public SeekTopAction() {
        this.p = AbstractDungeon.player;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            LeshyMod.logger.info("Start");

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.drawPile.group)
                tmp.addToRandomSpot(c);

            if (tmp.size() <= 1) {
                this.isDone = true;
                return;
            }
            LeshyMod.logger.info("Grid Select");

            AbstractDungeon.gridSelectScreen.open(tmp, 1, "Move a card to the top of your draw pile.", false);

            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            LeshyMod.logger.info("Selected");

            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            c.unhover();

            this.p.drawPile.removeCard(c);
            this.p.drawPile.addToTop(c);

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.isDone = true;
            return;

        }
        tickDuration();
    }
}
