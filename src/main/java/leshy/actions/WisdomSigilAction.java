package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.cards.*;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractWisdomCard;

import java.util.ArrayList;
import java.util.HashSet;

public class WisdomSigilAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public WisdomSigilAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            ArrayList<AbstractCard> deck = AbstractDungeon.player.drawPile.group;

            HashSet<AbstractCreatureCard.Sigils> sigils = new HashSet<>();

            int i = deck.size()-1;
            while(i>=0 && i>deck.size()-4){
                AbstractCard c = deck.get(i);
                c.applyPowers();
                if(c instanceof AbstractCreatureCard)
                    sigils.addAll(((AbstractCreatureCard) c).current);
                i--;
            }

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            if(sigils.contains(AbstractCreatureCard.Sigils.AIRBORNE))
                tmp.addToTop(new WisdomAirborne());
            if(sigils.contains(AbstractCreatureCard.Sigils.AMORPHOUS))
                tmp.addToTop(new WisdomAmorphous());
            if(sigils.contains(AbstractCreatureCard.Sigils.ANT_SPAWNER))
                tmp.addToTop(new WisdomAntSpawner());
            if(sigils.contains(AbstractCreatureCard.Sigils.ARMORED))
                tmp.addToTop(new WisdomArmored());
            if(sigils.contains(AbstractCreatureCard.Sigils.BEES_WITHIN))
                tmp.addToTop(new WisdomBeesWithin());
            if(sigils.contains(AbstractCreatureCard.Sigils.BIFURCATED))
                tmp.addToTop(new WisdomBifurcated());
            if(sigils.contains(AbstractCreatureCard.Sigils.BONE_KING))
                tmp.addToTop(new WisdomBoneKing());
            if(sigils.contains(AbstractCreatureCard.Sigils.BONE_DIGGER))
                tmp.addToTop(new WisdomBoneDigger());
            if(sigils.contains(AbstractCreatureCard.Sigils.BROOD_PARASITE))
                tmp.addToTop(new WisdomBroodParasite());
            if(sigils.contains(AbstractCreatureCard.Sigils.DAM_BUILDER))
                tmp.addToTop(new WisdomDamBuilder());
            if(sigils.contains(AbstractCreatureCard.Sigils.DOUBLE_STRIKE))
                tmp.addToTop(new WisdomDoubleStrike());
            if(sigils.contains(AbstractCreatureCard.Sigils.FECUNDITY))
                tmp.addToTop(new WisdomFecundity());
            if(sigils.contains(AbstractCreatureCard.Sigils.FLEDGLING))
                tmp.addToTop(new WisdomFledgling());
            if(sigils.contains(AbstractCreatureCard.Sigils.GUARDIAN))
                tmp.addToTop(new WisdomGuardian());
            if(sigils.contains(AbstractCreatureCard.Sigils.HOARDER))
                tmp.addToTop(new WisdomHoarder());
            if(sigils.contains(AbstractCreatureCard.Sigils.LEADER))
                tmp.addToTop(new WisdomLeader());
            if(sigils.contains(AbstractCreatureCard.Sigils.LOOSE_TAIL))
                tmp.addToTop(new WisdomLooseTail());
            if(sigils.contains(AbstractCreatureCard.Sigils.MANY_LIVES))
                tmp.addToTop(new WisdomManyLives());
            if(sigils.contains(AbstractCreatureCard.Sigils.MIGHTY_LEAP))
                tmp.addToTop(new WisdomMightyLeap());
            if(sigils.contains(AbstractCreatureCard.Sigils.RABBIT_HOLE))
                tmp.addToTop(new WisdomRabbitHole());
            if(sigils.contains(AbstractCreatureCard.Sigils.RAMPAGER))
                tmp.addToTop(new WisdomRampager());
            if(sigils.contains(AbstractCreatureCard.Sigils.SHARP_QUILLS))
                tmp.addToTop(new WisdomSharpQuills());
            if(sigils.contains(AbstractCreatureCard.Sigils.STINKY))
                tmp.addToTop(new WisdomStinky());
            if(sigils.contains(AbstractCreatureCard.Sigils.TOUCH_OF_DEATH))
                tmp.addToTop(new WisdomTouchOfDeath());
            if(sigils.contains(AbstractCreatureCard.Sigils.TRIFURCATED))
                tmp.addToTop(new WisdomTrifurcated());
            if(sigils.contains(AbstractCreatureCard.Sigils.TRINKET_BEARER))
                tmp.addToTop(new WisdomTrinketBearer());
            if(sigils.contains(AbstractCreatureCard.Sigils.UNKILLABLE))
                tmp.addToTop(new WisdomUnkillable());
            if(sigils.contains(AbstractCreatureCard.Sigils.WATERBORNE))
                tmp.addToTop(new WisdomWaterborne());
            if(sigils.contains(AbstractCreatureCard.Sigils.WORTHY_SACRIFICE))
                tmp.addToTop(new WisdomWorthySacrifice());

            if(tmp.isEmpty()){
                this.isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 1, TrialOfWisdom.cardStrings.EXTENDED_DESCRIPTION[1], false);
            tickDuration();

            return;

        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            if(card instanceof AbstractWisdomCard)
                addToBot(new WisdomCreatureAction((AbstractWisdomCard) card));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.isDone = true;

        }

        tickDuration();

    }

}













