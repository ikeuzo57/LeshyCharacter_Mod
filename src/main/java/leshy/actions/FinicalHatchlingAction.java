package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.LeshyMod;
import leshy.cards.CuriousEgg;
import leshy.cards.Hydra;

public class FinicalHatchlingAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public CuriousEgg egg;
    public int magicNumber;

    public FinicalHatchlingAction(CuriousEgg card, int magicNumber){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;

        this.egg = card;
        this.magicNumber = magicNumber;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            if(LeshyMod.fullSets >= this.magicNumber){

                Hydra hydra = new Hydra();
                hydra.transform(this.egg);
                hydra.baseAttack += (this.egg.baseAttack - this.egg.trueBaseAttack);
                hydra.baseHealth += (this.egg.baseHealth - this.egg.trueBaseHealth);
                AbstractDungeon.player.drawPile.group.add(AbstractDungeon.player.drawPile.group.indexOf(this.egg), hydra);
                AbstractDungeon.player.drawPile.removeCard(this.egg);

            }

            this.isDone = true;
            return;

        }

        tickDuration();

    }

}













