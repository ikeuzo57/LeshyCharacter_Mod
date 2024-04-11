package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import leshy.cards.Bee;
import leshy.orbs.CreatureOrb;

public class HoneycombAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public HoneycombAction(){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update() {

        for(AbstractOrb o : AbstractDungeon.player.orbs){
            if(o instanceof EmptyOrbSlot){
                addToTop(new SummonCreatureAction(new CreatureOrb(new Bee())));
                break;
            }
        }

        this.isDone = true;

    }

}













