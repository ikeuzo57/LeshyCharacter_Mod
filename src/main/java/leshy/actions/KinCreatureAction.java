package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.ArrayList;

public class KinCreatureAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;


    public KinCreatureAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {


            ArrayList<AbstractCard> deck = AbstractDungeon.player.drawPile.group;

            int avian = 0;
            int canine = 0;
            int hooved = 0;
            int reptile = 0;
            int insect = 0;
            int amalgam = 0;
            int squirrel = 0;

            int i = deck.size()-1;
            while(i>=0 && i>deck.size()-4){
                if(deck.get(i) instanceof AbstractCreatureCard){
                    AbstractCreatureCard c = (AbstractCreatureCard) deck.get(i);
                    switch (c.tribe){
                        case AVIAN:
                            avian++;
                            break;
                        case CANINE:
                            canine++;
                            break;
                        case HOOVED:
                            hooved++;
                            break;
                        case ANT:
                        case INSECT:
                            insect++;
                            break;
                        case REPTILE:
                            reptile++;
                            break;
                        case AMALGAM:
                            amalgam++;
                            break;
                        case SQUIRREL:
                            squirrel++;
                            break;
                        case NONE:
                            if(LeshyMod.cawCaw)
                                avian++;
                            break;
                    }
                }
                i--;
            }

            LeshyMod.logger.info("Avian : " + avian);
            LeshyMod.logger.info("Canine : " + canine);
            LeshyMod.logger.info("Hooved : " + hooved);
            LeshyMod.logger.info("Insect : " + insect);
            LeshyMod.logger.info("Reptile : " + reptile);
            LeshyMod.logger.info("Squirrel : " + squirrel);
            LeshyMod.logger.info("Amalgam : " + amalgam);

            ArrayList<AbstractCard> tmp = new ArrayList<>();
            i = deck.size()-1;
            while(i>=0 && i>deck.size()-4){
                if(deck.get(i) instanceof AbstractCreatureCard){
                    AbstractCreatureCard c = (AbstractCreatureCard) deck.get(i);
                    AbstractCreatureCard.CreatureTribe tribe = c.tribe;
                    if(tribe == AbstractCreatureCard.CreatureTribe.NONE && LeshyMod.cawCaw)
                        tribe = AbstractCreatureCard.CreatureTribe.AVIAN;
                    switch (tribe){
                        case AVIAN:
                            if(avian+amalgam >= 2)
                                tmp.add(c);
                            break;
                        case CANINE:
                            if(canine+amalgam >= 2)
                                tmp.add(c);
                            break;
                        case HOOVED:
                            if(hooved+amalgam >= 2)
                                tmp.add(c);
                            break;
                        case ANT:
                        case INSECT:
                            if(insect+amalgam >= 2)
                                tmp.add(c);
                            break;
                        case REPTILE:
                            if(reptile+amalgam >= 2)
                                tmp.add(c);
                            break;
                        case SQUIRREL:
                            if(squirrel+amalgam >= 2)
                                tmp.add(c);
                            break;
                        case AMALGAM:
                            if(avian+canine+hooved+insect+reptile+squirrel+amalgam >= 2)
                                tmp.add(c);
                            break;
                    }
                }
                i--;
            }

            for(AbstractCard c : tmp){

                if (AbstractDungeon.player.hand.size() == 10) {
                    AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                    AbstractDungeon.player.createHandIsFullDialog();
                    continue;
                }

                AbstractDungeon.player.drawPile.moveToHand(c);
            }

            this.isDone = true;
            return;

        }

        tickDuration();

    }

}













