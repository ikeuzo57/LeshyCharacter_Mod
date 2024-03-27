package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.cards.Aquasquirrel;
import leshy.cards.Squirrel;
import leshy.cards.Roadkill;

public class RoadkillAction extends AbstractGameAction {

    private float startingDuration;
    private Roadkill roadkill;

    public RoadkillAction(Roadkill card) {
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.roadkill = card;
    }

    public void update() {
        if (this.duration == this.startingDuration) {

            AbstractCard card = null;
            for(AbstractCard c : AbstractDungeon.player.hand.group){
                if(c instanceof Squirrel || c instanceof Aquasquirrel){
                    card = c;
                    break;
                }
            }

            if(card != null){

                AbstractDungeon.player.hand.moveToExhaustPile(card);
                if(AbstractDungeon.player.hand.contains(roadkill))
                    AbstractDungeon.player.hand.moveToExhaustPile(roadkill);
                if(AbstractDungeon.player.discardPile.contains(roadkill))
                    AbstractDungeon.player.discardPile.moveToExhaustPile(roadkill);
                if(AbstractDungeon.player.drawPile.contains(roadkill))
                    AbstractDungeon.player.drawPile.moveToExhaustPile(roadkill);

                CardCrawlGame.dungeon.checkForPactAchievement();
                card.exhaustOnUseOnce = false;
                card.freeToPlayOnce = false;
            }

        }
        tickDuration();
    }
}

