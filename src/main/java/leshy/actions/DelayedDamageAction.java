package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.orbs.CreatureOrb;

public class DelayedDamageAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private final CreatureOrb orb;
    private final DamageInfo info;
    private final int damageAmount;

    public DelayedDamageAction(CreatureOrb o, DamageInfo info, int damageAmount){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.SPECIAL;
        this.orb = o;
        this.info = info;
        this.damageAmount = damageAmount;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {
            this.orb.onAttackedToChangeDamage(info, damageAmount);
            this.isDone = true;
        }

        tickDuration();

    }

}













