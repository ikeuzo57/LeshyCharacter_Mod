package leshy.actions;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.Glitch;
import leshy.relics.OldDataRelic;

import java.util.ArrayList;

public class GlitchAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public Glitch glitch;

    public GlitchAction(Glitch card){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;

        this.glitch = card;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {


            ArrayList<AbstractCreatureCard> list = new ArrayList<>();
            for(AbstractCard c : AbstractDungeon.player.drawPile.group){
                if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).isStatic && !((AbstractCreatureCard) c).bloodless)
                    list.add((AbstractCreatureCard) c);
            }
            if(!list.isEmpty()){
                AbstractCreatureCard card = list.get(AbstractDungeon.miscRng.random(list.size() - 1));
                card.isStatic = true;
                MultiCardPreview.clear(card);

                AbstractDungeon.player.drawPile.moveToExhaustPile(this.glitch);

                OldDataRelic.glitchedNames.add(card.name);

            }else{
                this.glitch.isStatic = true;
            }

            this.isDone = true;
            return;

        }

        tickDuration();

    }

}













