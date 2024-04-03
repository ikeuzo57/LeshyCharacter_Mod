package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import leshy.LeshyMod;
import leshy.cards.Amalgam;
import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.ArrayList;

public class CameraAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    public Amalgam card;

    public CameraAction(Amalgam card){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;

        this.card = card;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            if(this.card == null){
                this.isDone = true;
                return;
            }

            ArrayList<AbstractCard> list = new ArrayList<>();
            list.add(this.card);
            AbstractDungeon.cardRewardScreen.customCombatOpen(list, AbstractCreatureCard.SCREEN_DESCRIPTION[2], true);

            tickDuration();
            return;
        }

        if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {

            AbstractCard codexCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
            codexCard.current_x = -1000.0F * Settings.xScale;
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(codexCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.cardRewardScreen.discoveryCard = null;

            this.isDone = true;

        }

        tickDuration();

    }

}













