package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.cards.Aquasquirrel;
import leshy.cards.Roadkill;
import leshy.cards.Squirrel;
import leshy.relics.OldDataRelic;

public class GlitchedTipAction extends AbstractGameAction {

    private float startingDuration;

    private OldDataRelic odr;

    public GlitchedTipAction(OldDataRelic odr) {
        this.actionType = ActionType.EXHAUST;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.odr = odr;
    }

    public void update() {
        if (this.duration == this.startingDuration) {

            odr.updateDescription(AbstractDungeon.player.chosenClass);

        }
        tickDuration();
    }
}

