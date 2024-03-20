package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.orbs.CreatureOrb;

public class OnSummonAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private final CreatureOrb orb;

    public OnSummonAction(CreatureOrb o){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.SPECIAL;
        this.orb = o;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {
            this.orb.creatureCard.onSummon();
            this.isDone = true;
        }

        tickDuration();

    }

}













