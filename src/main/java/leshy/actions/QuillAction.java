package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.ArrayList;

public class QuillAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private boolean retrieveCard = false;

    public QuillAction(){
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

            ArrayList<AbstractCreatureCard> list = new ArrayList<>();
            for(AbstractCard c : AbstractDungeon.srcCommonCardPool.group)
                if(c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).costType == AbstractCreatureCard.CreatureCostType.BONE)
                    list.add((AbstractCreatureCard) c);
            for(AbstractCard c : AbstractDungeon.srcUncommonCardPool.group)
                if(c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).costType == AbstractCreatureCard.CreatureCostType.BONE)
                    list.add((AbstractCreatureCard) c);
            for(AbstractCard c : AbstractDungeon.srcRareCardPool.group)
                if(c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).costType == AbstractCreatureCard.CreatureCostType.BONE)
                    list.add((AbstractCreatureCard) c);

            ArrayList<AbstractCard> tmp = new ArrayList<>();

            for(int i=0; i<3; i++){
                AbstractCreatureCard card = (AbstractCreatureCard) (list.remove(AbstractDungeon.cardRandomRng.random(list.size()-1))).makeCopy();
                tmp.add(card);
            }

            AbstractDungeon.cardRewardScreen.customCombatOpen(tmp, "Add one to hand.", false);

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



}













