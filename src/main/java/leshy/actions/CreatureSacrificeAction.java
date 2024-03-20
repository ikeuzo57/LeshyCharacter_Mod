package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.orbs.CreatureOrb;

public class CreatureSacrificeAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private final CreatureOrb orb;

    public CreatureSacrificeAction(CreatureOrb o){
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.orb = o;
    }
    
    @Override
    public void update() {

        AbstractDungeon.player.orbs.remove(this.orb);
        AbstractDungeon.player.orbs.add(0, this.orb);
        AbstractDungeon.player.evokeOrb();

        this.orb.creatureCard.bounce = false;

        this.isDone = true;

    }

}













