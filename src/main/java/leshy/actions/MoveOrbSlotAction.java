package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import leshy.orbs.CreatureOrb;

public class MoveOrbSlotAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private final CreatureOrb orb;
    private final int slot;
    private final boolean updatePos;

    public MoveOrbSlotAction(CreatureOrb o, int slot){
        this(o, slot, true);
    }

    public MoveOrbSlotAction(CreatureOrb o, int slot, boolean updatePos){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.SPECIAL;
        this.orb = o;
        this.slot = slot;
        this.updatePos = updatePos;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            AbstractDungeon.player.orbs.remove(this.orb);

            for(int i=0; i<AbstractDungeon.player.orbs.size(); i++){
                if(AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot){
                    if(i < this.slot){
                        AbstractDungeon.player.orbs.add(i, this.orb);
                        if(updatePos)
                            addToTop(new UpdatePositionsAction());
                        this.isDone = true;
                        return;
                    }
                }
            }
            AbstractDungeon.player.orbs.add(this.slot, this.orb);
            if(updatePos)
                addToTop(new UpdatePositionsAction());

            this.isDone = true;
            return;
        }

        tickDuration();

    }

}













