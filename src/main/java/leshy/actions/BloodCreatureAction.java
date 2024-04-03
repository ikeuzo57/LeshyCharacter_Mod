package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.cards.TrialOfBlood;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.orbs.CreatureOrb;

import java.util.ArrayList;
import java.util.Arrays;

public class BloodCreatureAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    public int totalBloodCost;

    public BloodCreatureAction(int totalBloodCost){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.totalBloodCost = totalBloodCost;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            ArrayList<AbstractCreatureCard> list = new ArrayList<>();
            for(AbstractCard c : AbstractDungeon.srcCommonCardPool.group)
                if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).bloodless)
                    list.add((AbstractCreatureCard) c);
            for(AbstractCard c : AbstractDungeon.srcUncommonCardPool.group)
                if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).bloodless)
                    list.add((AbstractCreatureCard) c);
            for(AbstractCard c : AbstractDungeon.srcRareCardPool.group)
                if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).bloodless)
                    list.add((AbstractCreatureCard) c);

            ArrayList<AbstractCard> tmp = new ArrayList<>();

            for(int i=0; i<3; i++){
                AbstractCreatureCard card = (AbstractCreatureCard) (list.remove(AbstractDungeon.cardRandomRng.random(list.size()-1))).makeCopy();
                for(int j=0; j<this.totalBloodCost; j++)
                    card.upgrade();
                addRandomSigils(card, 2);
                tmp.add(card);
            }

            AbstractDungeon.cardRewardScreen.customCombatOpen(tmp, TrialOfBlood.cardStrings.EXTENDED_DESCRIPTION[1], false);
            tickDuration();

            return;

        }

        if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {

            AbstractCard card = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();

            if(card instanceof AbstractCreatureCard){
                ((AbstractCreatureCard) card).baseFleeting = true;
                addToBot(new SummonCreatureAction(new CreatureOrb((AbstractCreatureCard) card)));
            }

            AbstractDungeon.cardRewardScreen.discoveryCard = null;

            this.isDone = true;

        }

        tickDuration();

    }

    public static void addRandomSigils(AbstractCreatureCard card, int num){

        if(num <= 0)
            return;

        ArrayList<AbstractCreatureCard.Sigils> sigils = new ArrayList<>(Arrays.asList(AbstractCreatureCard.Sigils.values()));

        int numAdded = 0;

        while(numAdded < num && !sigils.isEmpty()){
            AbstractCreatureCard.Sigils s = sigils.get(AbstractDungeon.miscRng.random(sigils.size()-1));
            if(s == AbstractCreatureCard.Sigils.ARMORED || s == AbstractCreatureCard.Sigils.DOUBLE_STRIKE || s == AbstractCreatureCard.Sigils.FECUNDITY || s == AbstractCreatureCard.Sigils.UNKILLABLE || s == AbstractCreatureCard.Sigils.HOARDER)
                if(AbstractDungeon.miscRng.randomBoolean())
                    continue;

            sigils.remove(s);
            if(!card.innate.contains(s) && !card.gained.contains(s)){
                card.gained.add(s);
                card.current.add(s);
                numAdded++;
            }
        }

        card.initializeDescription();

    }

}













