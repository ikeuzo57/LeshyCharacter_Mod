package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.orbs.CreatureOrb;

public class UpdatePositionsAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public UpdatePositionsAction(){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update() {

        int maxOrbs = p.orbs.size();
        for(int i=0; i<p.orbs.size(); i++){
            AbstractOrb o = p.orbs.get(i);
            if(o instanceof CreatureOrb){
                o.setSlot(i, maxOrbs);
            }
        }

        this.isDone = true;

    }

}













