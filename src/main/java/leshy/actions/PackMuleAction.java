package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import leshy.LeshyMod;
import leshy.cards.PackMule;
import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.ArrayList;

public class PackMuleAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private boolean retrieveCard = false;

    public PackMuleAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {

        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }

        if (this.duration == Settings.ACTION_DUR_FAST) {

            AbstractDungeon.cardRewardScreen.customCombatOpen(getOptions(), AbstractCreatureCard.SCREEN_DESCRIPTION[3], true);

            tickDuration();
            return;

        }

        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard codexCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                codexCard.current_x = -1000.0F * Settings.xScale;
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(codexCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }

        tickDuration();

    }


    public ArrayList<AbstractCard> getOptions(){

        ArrayList<AbstractCreatureCard> list = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.srcCommonCardPool.group)
            if(c instanceof AbstractCreatureCard)
                list.add((AbstractCreatureCard) c);
        for(AbstractCard c : AbstractDungeon.srcUncommonCardPool.group)
            if(c instanceof AbstractCreatureCard)
                list.add((AbstractCreatureCard) c);
        for(AbstractCard c : AbstractDungeon.srcRareCardPool.group)
            if(c instanceof AbstractCreatureCard)
                list.add((AbstractCreatureCard) c);

        ArrayList<AbstractCard> oneBlood = new ArrayList<>();
        for(AbstractCreatureCard c : list)
            if(c.costType == AbstractCreatureCard.CreatureCostType.BLOOD && c.extraCost == 1)
                oneBlood.add(c);

        ArrayList<AbstractCard> twoBlood = new ArrayList<>();
        for(AbstractCreatureCard c : list)
            if(c.costType == AbstractCreatureCard.CreatureCostType.BLOOD && c.extraCost == 2)
                twoBlood.add(c);

        ArrayList<AbstractCard> bone = new ArrayList<>();
        for(AbstractCreatureCard c : list)
            if(c.costType == AbstractCreatureCard.CreatureCostType.BONE)
                bone.add(c);


        ArrayList<AbstractCard> tmp = new ArrayList<>();
        tmp.add(LeshyMod.getSquirrel());
        tmp.add(oneBlood.get(AbstractDungeon.cardRandomRng.random(oneBlood.size()-1)));
        tmp.add(twoBlood.get(AbstractDungeon.cardRandomRng.random(twoBlood.size()-1)));
        tmp.add(bone.get(AbstractDungeon.cardRandomRng.random(bone.size()-1)));

        return tmp;


    }



}













