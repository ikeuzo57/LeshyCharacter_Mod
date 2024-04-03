package leshy.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.patches.LeshyEventPatch;
import leshy.relics.MycologistMaskRelic;

import java.util.ArrayList;

import static leshy.LeshyMod.makeEventPath;

public class MycologistsEvent extends AbstractImageEvent {


    public static final String ID = LeshyMod.makeID("MycologistsEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("MycologistsEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private boolean selectBase = false;
    private boolean selectDuplicate = false;
    private boolean selectFreeCopy = false;

    private float statRatio = 0.5F;

    private AbstractCreatureCard base;

    public MycologistsEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);


        this.hasDialog = true;
        this.hasFocus = true;
        this.combatTime = false;

        if(AbstractDungeon.ascensionLevel >= 15)
            statRatio = 0.25F;

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

                        if(getOptions().isEmpty()){
                            this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                            this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                            this.imageEventText.clearRemainingOptions();
                            screenNum = 3;
                        }else{
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                            this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                            this.imageEventText.clearRemainingOptions();
                            screenNum = 1;
                        }

                        break;

                    case 1:
                        openMap();
                        break;

                }
                break;

            //Has Duplicates screens
            case 1:
                selectBase = true;
                AbstractDungeon.gridSelectScreen.open(getOptions(), 1, DESCRIPTIONS[8], false, false, false, false);
                this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                this.imageEventText.clearRemainingOptions();
                screenNum = 2;
                break;

            case 2:
                selectDuplicate = true;
                AbstractDungeon.gridSelectScreen.open(getDuplicates(base), 1, DESCRIPTIONS[9] + LeshyEventPatch.toPercentage(statRatio) + DESCRIPTIONS[10], false, false, false, false);
                this.imageEventText.updateBodyText(DESCRIPTIONS[3] + base.name + DESCRIPTIONS[4]);
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                screenNum = 4;
                break;

            //No Duplicates screen
            case 3:
                selectFreeCopy = true;
                AbstractDungeon.gridSelectScreen.open(getFreeCopyOptions(), 1, DESCRIPTIONS[11], false, false, false, false);
                this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                screenNum = 4;
                break;

            //Error screen
            case 4:
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

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && selectBase) {
            selectBase = false;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            if(c instanceof AbstractCreatureCard) {
                base = (AbstractCreatureCard) c;
            }else{
                this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                screenNum = 4;
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && selectDuplicate) {
            selectDuplicate = false;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            if(c instanceof AbstractCreatureCard) {
                base.mycologistOption((AbstractCreatureCard) c, statRatio);
                CardCrawlGame.sound.play("BLOOD_SPLAT");
                CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(base.makeStatEquivalentCopy()));
            }else{
                this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                screenNum = 4;
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && selectFreeCopy) {
            selectFreeCopy = false;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            if(c instanceof AbstractCreatureCard) {
                AbstractDungeon.player.masterDeck.addToTop(c);
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            }else{
                this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                screenNum = 4;
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }



    }


    public static CardGroup getOptions(){

        CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if(c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).mushroomCount == 1)
                for(AbstractCard c2 : AbstractDungeon.player.masterDeck.group)
                    if(c.cardID.equals(c2.cardID) && !c.uuid.equals(c2.uuid) && ((AbstractCreatureCard) c2).mushroomCount == 1){
                        list.addToTop(c);
                        break;
                    }


        return list;
    }

    public static CardGroup getDuplicates(AbstractCard base){

        CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if(c.cardID.equals(base.cardID) && !c.uuid.equals(base.uuid) && ((AbstractCreatureCard) c).mushroomCount == 1)
                list.addToTop(c);

        return list;
    }

    public static CardGroup getFreeCopyOptions(){

        CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        ArrayList<AbstractCreatureCard> options = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if(c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).mycologistReroll > 0 && ((AbstractCreatureCard) c).mushroomCount == 1)
                options.add((AbstractCreatureCard) c.makeCopy());


        for(AbstractRelic r : AbstractDungeon.player.relics)
            if(r instanceof MycologistMaskRelic){

                for(AbstractCard c : options)
                    list.addToTop(c);

                return list;
            }


        while(list.size() < 3 && !options.isEmpty()){

            if(3 - list.size() >= options.size()){
                for(AbstractCard c : options)
                    list.addToTop(c);
                return list;
            }

            int index = AbstractDungeon.miscRng.random(options.size()-1);
            AbstractCreatureCard card = options.remove(index);
            if(AbstractDungeon.miscRng.randomBoolean(card.mycologistReroll))
                list.addToTop(card);

        }

        return list;
    }

}
