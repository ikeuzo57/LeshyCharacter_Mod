package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractPeltCard;

import java.util.ArrayList;

public class VoucherAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private boolean retrieveCard = false;

    public VoucherAction(){
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

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            ArrayList<AbstractCreatureCard> common = new ArrayList<>();
            for(AbstractCard c : AbstractDungeon.srcCommonCardPool.group)
                if(c instanceof AbstractCreatureCard && !(c instanceof AbstractPeltCard))
                    common.add((AbstractCreatureCard) c);

            while(tmp.size() < 4){

                if(4 - tmp.size() >= common.size()){
                    for(AbstractCard c : common)
                        tmp.addToTop(c);
                }else{
                    AbstractCreatureCard card = (AbstractCreatureCard) common.remove(AbstractDungeon.cardRandomRng.random(common.size() - 1)).makeCopy();

                    if(AbstractDungeon.cardRandomRng.randomBoolean(card.spawnRate))
                        tmp.addToTop(card);
                }

            }

            ArrayList<AbstractCreatureCard> uncommon = new ArrayList<>();
            for(AbstractCard c : AbstractDungeon.srcUncommonCardPool.group)
                if(c instanceof AbstractCreatureCard && !(c instanceof AbstractPeltCard))
                    uncommon.add((AbstractCreatureCard) c);

            while(tmp.size() < 5){

                if(5 - tmp.size() >= uncommon.size()){
                    for(AbstractCard c : uncommon)
                        tmp.addToTop(c);
                }else{
                    AbstractCreatureCard card = (AbstractCreatureCard) uncommon.remove(AbstractDungeon.cardRandomRng.random(uncommon.size() - 1)).makeCopy();

                    if(AbstractDungeon.cardRandomRng.randomBoolean(card.spawnRate))
                        tmp.addToTop(card);
                }

            }

            AbstractDungeon.gridSelectScreen.open(tmp, 1, false, "Choose one to gain.");

            tickDuration();
            return;

        }

        if (!this.retrieveCard) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                AbstractCard codexCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                codexCard.current_x = -1000.0F * Settings.xScale;
                addToBot(new MakeTempCardInHandAction(codexCard));
                AbstractDungeon.player.masterDeck.addToTop(codexCard.makeSameInstanceOf());
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            this.retrieveCard = true;
        }

        tickDuration();

    }



}













