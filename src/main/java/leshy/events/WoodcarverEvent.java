package leshy.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractTotemBaseCard;

import java.util.ArrayList;

import static leshy.LeshyMod.makeEventPath;

public class WoodcarverEvent extends AbstractImageEvent {


    public static final String ID = LeshyMod.makeID("WoodcarverEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("WoodcarverEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private boolean selectTotem = false;

    private AbstractCreatureCard sacrifice;

    public WoodcarverEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);


        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[0]);


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
                switch (i) {
                    case 0:
                        selectTotem = true;
                        AbstractDungeon.cardRewardScreen.customCombatOpen(getTotemOptions(), "Add one to hand.", false);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[0]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;

                    case 1:
                        openMap();
                        break;

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

        if (AbstractDungeon.cardRewardScreen.discoveryCard != null && selectTotem) {
            selectTotem = false;
            AbstractCard c = AbstractDungeon.cardRewardScreen.discoveryCard;
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }


    }



    public static ArrayList<AbstractCard> getTotemOptions(){

        int count = 4;
        if (AbstractDungeon.ascensionLevel >= 15)
            count = 3;

        ArrayList<AbstractTotemBaseCard> uncommon = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.srcUncommonCardPool.group)
            if(c instanceof AbstractTotemBaseCard)
                uncommon.add((AbstractTotemBaseCard) c.makeCopy());

        ArrayList<AbstractTotemBaseCard> rare = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.srcRareCardPool.group)
            if(c instanceof AbstractTotemBaseCard)
                rare.add((AbstractTotemBaseCard) c.makeCopy());

        ArrayList<AbstractCard> list = new ArrayList<>();
        while(list.size() < count){
            if(AbstractDungeon.cardRandomRng.randomBoolean(0.8F)){
                LeshyMod.logger.info("Uncommon");
                list.add(uncommon.remove(AbstractDungeon.cardRandomRng.random(uncommon.size()-1)));
            }else{
                LeshyMod.logger.info("Rare");
                AbstractTotemBaseCard card = rare.get(AbstractDungeon.cardRandomRng.random(rare.size()-1));
                if(AbstractDungeon.miscRng.randomBoolean(card.spawnRate)){
                    list.add(card);
                    rare.remove(card);
                    LeshyMod.logger.info("Added");
                }
            }
        }
        return list;
    }

}
