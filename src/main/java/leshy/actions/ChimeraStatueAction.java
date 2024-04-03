package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.relics.ChimeraStatueRelic;
import leshy.relics.OldDataRelic;

public class ChimeraStatueAction extends AbstractGameAction {

    private float startingDuration;

    private ChimeraStatueRelic r;

    public ChimeraStatueAction(ChimeraStatueRelic r) {
        this.actionType = ActionType.EXHAUST;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.r = r;
    }

    public void update() {
        if (this.duration == this.startingDuration) {

            r.updateTribeCount();

        }
        tickDuration();
    }
}

