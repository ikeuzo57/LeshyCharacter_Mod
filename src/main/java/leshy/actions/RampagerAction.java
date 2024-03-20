package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import leshy.LeshyMod;
import leshy.orbs.CreatureOrb;

public class RampagerAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private final CreatureOrb orb;

    public RampagerAction(CreatureOrb o){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.SPECIAL;
        this.orb = o;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            int curr = AbstractDungeon.player.orbs.indexOf(this.orb);
            LeshyMod.logger.info("Current : " + curr);
            if(curr < 0){
                this.isDone = true;
                return;
            }

            int leftLimit = AbstractDungeon.player.orbs.size()-1;

            for(int i=0; i<AbstractDungeon.player.orbs.size(); i++){
                if(AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot){
                    LeshyMod.logger.info("Left Limit : " + (i-1));
                    leftLimit = i-1;
                    break;
                }
            }

            if(leftLimit <= 0){
                LeshyMod.logger.info("Only 1 spot");
                this.orb.creatureCard.rampageRight = false;
                this.isDone = true;
                return;
            }

            if(curr == 0)
                this.orb.creatureCard.rampageRight = false;

            if(curr == leftLimit)
                this.orb.creatureCard.rampageRight = true;

            AbstractDungeon.player.orbs.remove(this.orb);

            if(this.orb.creatureCard.rampageRight)
                AbstractDungeon.player.orbs.add(curr-1, this.orb);
            else
                AbstractDungeon.player.orbs.add(curr+1, this.orb);

            addToTop(new UpdatePositionsAction());

            this.isDone = true;
            return;
        }

        tickDuration();

    }

}













