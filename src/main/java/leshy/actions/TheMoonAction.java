package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.cards.TheMoon;
import leshy.orbs.CreatureOrb;

public class TheMoonAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public TheMoonAction(){
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_MED;

    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {


            for(AbstractOrb o : p.orbs){
                if(o instanceof CreatureOrb){
                    addToBot(new CreatureSacrificeAction((CreatureOrb) o));
                }
            }
            addToBot(new DecreaseMaxOrbAction(AbstractDungeon.player.maxOrbs-1));

            addToBot(new SummonCreatureAction(new CreatureOrb(new TheMoon())));


            this.isDone = true;
            return;

        }

        tickDuration();

    }

}













