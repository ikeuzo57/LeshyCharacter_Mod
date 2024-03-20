package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.powers.TargetPower;

public class TargetAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    public TargetAction(AbstractMonster target){
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.POWER;
        this.target = target;
    }
    
    @Override
    public void update() {

        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if(m.currentHealth > 0 && !m.isDeadOrEscaped()){
                for(AbstractPower ap : m.powers){
                    if(ap instanceof TargetPower){
                        addToBot(new RemoveSpecificPowerAction(m, m, ap));
                    }
                }
            }
        }

        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new TargetPower(target, AbstractDungeon.player)));

        this.isDone = true;

    }

}













