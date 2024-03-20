package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.orbs.CreatureOrb;

public class BuffAllCreaturesAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;



    public BuffAllCreaturesAction(int amount){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.amount = amount;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            for(AbstractOrb o : p.orbs){
                if(o instanceof CreatureOrb){
                    ((CreatureOrb) o).creatureCard.baseAttack += amount;
                    ((CreatureOrb) o).creatureCard.baseHealth += amount;
                    ((CreatureOrb) o).creatureCard.applyPowers();
                }
            }

            this.isDone = true;
            return;

        }

        tickDuration();

    }

}













