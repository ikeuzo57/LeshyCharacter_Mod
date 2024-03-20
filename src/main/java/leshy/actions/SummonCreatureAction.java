package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.orbs.CreatureOrb;

public class SummonCreatureAction extends AbstractGameAction {

    private static final AbstractPlayer p = AbstractDungeon.player;

    private final CreatureOrb orb;
    private final int slot;

    public SummonCreatureAction(CreatureOrb o){
        this(o, AbstractDungeon.player.orbs.size()-1);
    }

    public SummonCreatureAction(CreatureOrb o, int slot){
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
        this.orb = o;
        this.slot = slot;
        if(orb.creatureCard.purgeOnUse){
            orb.creatureCard = (AbstractCreatureCard) orb.creatureCard.makeStatEquivalentCopy();
            orb.creatureCard.orb = orb;
        }
        orb.creatureCard.beginGlowing();
    }
    
    @Override
    public void update() {

        p.hand.removeCard(orb.creatureCard);
        p.drawPile.removeCard(orb.creatureCard);
        p.discardPile.removeCard(orb.creatureCard);


        if(AbstractDungeon.player.hasEmptyOrb()){
            addToTop(new ChannelCreatureOrbAction(this.orb, slot));
            addToBot(new OnSummonAction(orb));
            addToBot(new UpdatePositionsAction());
        }else{
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, "No empty orb slot", true));
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmp.addToTop(orb.creatureCard);
            orb.creatureCard.orb = null;
            if(p.hand.size() < 10) {
                tmp.moveToHand(orb.creatureCard);
            }else{
                tmp.moveToDiscardPile(orb.creatureCard);
            }
            addToTop(new HandCheckAction());
        }

        this.isDone = true;

    }


}













