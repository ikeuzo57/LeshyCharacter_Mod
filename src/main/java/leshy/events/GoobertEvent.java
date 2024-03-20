package leshy.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import leshy.LeshyMod;
import leshy.actions.BloodCreatureAction;
import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.ArrayList;

import static leshy.LeshyMod.makeEventPath;

public class GoobertEvent extends AbstractImageEvent {


    public static final String ID = LeshyMod.makeID("GoobertEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("GoobertEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private boolean selectPaint = false;

    public GoobertEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        if(!getPaintOptions().isEmpty())
            this.imageEventText.setDialogOption(OPTIONS[0]);
        else
            this.imageEventText.setDialogOption(OPTIONS[2], true);


        this.hasDialog = true;
        this.hasFocus = true;
        this.combatTime = false;

    }

    @Override
    public void onEnterRoom() {
        GenericEventDialog.show();
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {

            //Intro screen
            case 0:

                if(!getPaintOptions().isEmpty()){
                    selectPaint = true;
                    AbstractDungeon.gridSelectScreen.open(getPaintOptions(), 1, "Select a creature to paint.", false, false, false, false);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                    this.imageEventText.clearRemainingOptions();
                    screenNum = 1;
                }else{
                    openMap();
                }

                break;

            case 1:
                openMap();
                break;

        }
    }

    @Override
    public void render(SpriteBatch sb) {
        this.imageEventText.render(sb);
    }

    public void update() {
        super.update();

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && selectPaint) {
            selectPaint = false;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            if(c instanceof AbstractCreatureCard){
                AbstractCreatureCard paint = (AbstractCreatureCard) c.makeStatEquivalentCopy();

                ArrayList<AbstractCreatureCard.Sigils> gained = new ArrayList<>(paint.gained);
                if(!gained.isEmpty()) {
                    AbstractCreatureCard.Sigils toRemove = gained.get(AbstractDungeon.cardRng.random(gained.size() - 1));
                    paint.gained.remove(toRemove);
                    paint.current.remove(toRemove);
                }

                BloodCreatureAction.addRandomSigils(paint, 1);

                float ratio = 3F;
                if (AbstractDungeon.ascensionLevel >= 15){
                    ratio = 2F;
                }

                int increaseAttack = paint.baseAttack - paint.trueBaseAttack;
                if(increaseAttack > 0)
                    paint.baseAttack = (int)(paint.baseAttack - ((float) increaseAttack/ratio));

                int increaseHealth = paint.baseHealth - paint.trueBaseHealth;
                if(increaseHealth > 0)
                    paint.baseHealth = (int)(paint.baseHealth - ((float) increaseHealth/ratio));

                int roll = AbstractDungeon.miscRng.random(paint.baseAttack + paint.baseHealth - 1)+1;
                boolean moveAttack = (roll <= paint.baseAttack);
                LeshyMod.logger.info("Roll : " + roll);
                LeshyMod.logger.info("Move Attack : " + moveAttack);
                int maxMove = moveAttack ? paint.baseAttack-1 : paint.baseHealth-1;
                int toMove = 1;
                int moveRoll = AbstractDungeon.miscRng.random(maxMove);
                LeshyMod.logger.info("Move Roll : " + moveRoll);
                if(maxMove > 0)
                    toMove = moveRoll/3 + 1;

                LeshyMod.logger.info("To Move : " + toMove);
                if(!moveAttack)
                    toMove *= -1;

                paint.baseAttack -= toMove;
                paint.baseHealth += toMove;
                if(paint.baseAttack < 0)
                    paint.baseAttack = 0;
                if(paint.baseHealth < 1)
                    paint.baseHealth = 1;
                paint.attack = paint.baseAttack;
                paint.health = paint.baseHealth;

                paint.initializeDescription();

                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(paint, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }


    }



    public static CardGroup getPaintOptions(){
        CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).bloodless){
                list.addToTop(c);
            }
        }
        return list;
    }



}
