package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.powers.TargetPower;

public class SilentRemovePowerAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;
    private final AbstractPower power;

    public SilentRemovePowerAction(AbstractPower power){
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.POWER;
        this.power = power;
    }
    
    @Override
    public void update() {

        power.onRemove();
        power.owner.powers.remove(power);
        AbstractDungeon.onModifyPower();
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            o.updateDescription();
        }

        this.isDone = true;

    }

}













