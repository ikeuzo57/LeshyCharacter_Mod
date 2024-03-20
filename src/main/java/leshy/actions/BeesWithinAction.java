package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.cards.Bee;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.orbs.CreatureOrb;

public class BeesWithinAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    public AbstractCreatureCard source;

    public BeesWithinAction(AbstractCreatureCard source){
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.SPECIAL;
        this.source = source;
    }
    
    @Override
    public void update() {

        if(p.hand.size() < 10){
            Bee bee = new Bee();

            if(this.source.innate.contains(AbstractCreatureCard.Sigils.BEES_WITHIN))
                bee.transform(this.source);

            bee.applyPowers();
            addToTop(new MakeTempCardInHandAction(bee));
        }

        this.isDone = true;

    }

}













