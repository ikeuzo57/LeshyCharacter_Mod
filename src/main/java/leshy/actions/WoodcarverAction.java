package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import leshy.LeshyMod;
import leshy.cards.*;
import leshy.cards.abstracts.TotemHeadCard;
import leshy.relics.OldDataRelic;

public class WoodcarverAction extends AbstractGameAction {

    public WoodcarverAction(){
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            CardGroup tmp = getTotemOptions();

            if(tmp.isEmpty()){
                this.isDone = true;
                return;
            }

            int num = 1;
            for(AbstractRelic r : AbstractDungeon.player.relics){
                if(r instanceof OldDataRelic){
                    if(((OldDataRelic) r).head){
                        num = 3;
                    }
                    break;
                }
            }

            String msg = "Pick a totem head.";
            if(num > 1)
                msg = "Pick " + num + " totem heads.";


            AbstractDungeon.gridSelectScreen.open(tmp, num, msg, false);

            tickDuration();
            return;

        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){

                if(c instanceof TotemHeadCard){
                    addToBot(new TotemHeadAction((TotemHeadCard) c));
                }

            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.isDone = true;
            return;

        }
        tickDuration();

    }

    private static CardGroup getTotemOptions() {
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AvianTotem avian = new AvianTotem();
        CanineTotem canine = new CanineTotem();
        HoovedTotem hooved = new HoovedTotem();
        InsectTotem insect = new InsectTotem();
        ReptileTotem reptile = new ReptileTotem();
        tmp.addToTop(avian);
        tmp.addToTop(canine);
        tmp.addToTop(hooved);
        tmp.addToTop(insect);
        tmp.addToTop(reptile);
        return tmp;
    }

}













