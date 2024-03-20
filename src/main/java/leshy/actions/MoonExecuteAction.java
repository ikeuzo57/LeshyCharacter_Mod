package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.JudgementAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.powers.TargetPower;

public class MoonExecuteAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;
    private final int threshold;

    public MoonExecuteAction(AbstractMonster target, int threshold){
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.POWER;
        this.target = target;
        this.threshold = threshold;
    }
    
    @Override
    public void update() {

        if(!target.isDeadOrEscaped() && target.currentHealth < threshold){
            addToTop(new JudgementAction(target, threshold));
        }

        this.isDone = true;

    }

}













