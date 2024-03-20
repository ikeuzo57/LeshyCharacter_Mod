package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import leshy.orbs.CreatureOrb;

public class ChannelCreatureOrbAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private final CreatureOrb orb;
    private final int slot;
    private final boolean updatePos;

    public ChannelCreatureOrbAction(CreatureOrb o, int slot){
        this(o, slot, true);
    }

    public ChannelCreatureOrbAction(CreatureOrb o, int slot, boolean updatePos){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.SPECIAL;
        this.orb = o;
        this.slot = slot;
        this.updatePos = updatePos;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            int firstEmpty = -1;

            for(int i=0; i<AbstractDungeon.player.orbs.size(); i++){
                if(AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot){
                    firstEmpty = i;
                    break;
                }
            }

            if(firstEmpty == -1){
                this.isDone = true;
                return;
            }

            channel(orb, firstEmpty);

            this.isDone = true;
            return;
        }

        tickDuration();

    }

    public void channel(CreatureOrb orb, int firstEmpty){
        int index = Math.min(firstEmpty, this.slot);

        orb.cX = AbstractDungeon.player.orbs.get(index).cX;
        orb.cY = AbstractDungeon.player.orbs.get(index).cY;

        AbstractDungeon.player.orbs.remove(firstEmpty);
        AbstractDungeon.player.orbs.add(index, orb);

        orb.setSlot(index, AbstractDungeon.player.maxOrbs);
    }

}













