package leshy.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.BlackGoat;
import leshy.cards.Stinkbug;
import leshy.cards.Stoat;
import leshy.cards.StuntedWolf;
import leshy.relics.BoonOfTheBoneLordRelic;

import static leshy.LeshyMod.makeEventPath;

public class BoneAltarEvent extends AbstractImageEvent {


    public static final String ID = LeshyMod.makeID("BoneAltarEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("BoneAltarEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private boolean selectSacrifice = false;

    private AbstractCreatureCard sacrifice;

    public BoneAltarEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        if(!getSacrificeOptions(false).isEmpty())
            this.imageEventText.setDialogOption(OPTIONS[1]);
        else
            this.imageEventText.setDialogOption(OPTIONS[2], true);
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
                        selectSacrifice = true;
                        AbstractDungeon.gridSelectScreen.open(getSacrificeOptions(true), 1, "Select a creature to sacrifice.", false, false, false, false);
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

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && selectSacrifice) {
            selectSacrifice = false;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.effectsQueue.add(new PurgeCardEffect(c));

            int bones = -1;

            if(c instanceof BlackGoat){

                this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                bones = 8;

            }else if(c instanceof AbstractCreatureCard) {

                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                bones = 2;

            }else{
                this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                this.imageEventText.updateDialogOption(0, OPTIONS[0]);
                this.imageEventText.clearRemainingOptions();
                screenNum = 2;
            }

            boolean gain = true;
            for(AbstractRelic r : AbstractDungeon.player.relics){
                if(r instanceof BoonOfTheBoneLordRelic){
                    gain = false;
                    r.flash();
                    ((BoonOfTheBoneLordRelic) r).increase(bones);
                    break;
                }
            }
            if(gain){
                BoonOfTheBoneLordRelic relic = new BoonOfTheBoneLordRelic();
                relic.increase(bones);
                relic.instantObtain();
                CardCrawlGame.metricData.addRelicObtainData(relic);
            }

            MysteriousStonesEvent.resetNames();

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }


    }



    public static CardGroup getSacrificeOptions(boolean changeName){
        CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof AbstractCreatureCard){

                if(changeName){
                    if (c instanceof Stoat) {
                        switch (AbstractDungeon.miscRng.random(6)){
                            case 0:
                                c.name = "HMM...";
                                ((Stoat) c).titleScale = 1F;
                                break;
                            case 1:
                                c.name = "NOPE";
                                ((Stoat) c).titleScale = 1F;
                                break;
                            case 2:
                                c.name = "NO WAY";
                                ((Stoat) c).titleScale = 1F;
                                break;
                            case 3:
                                c.name = "NOPE NOPE NOPE";
                                ((Stoat) c).titleScale = 0.8F;
                                break;
                            case 4:
                                c.name = "STOP THIS";
                                ((Stoat) c).titleScale = 1F;
                                break;
                            case 5:
                                c.name = "PICK SOMETHING ELSE";
                                ((Stoat) c).titleScale = 0.6F;
                                break;
                            case 6:
                                c.name = "PLEASE NO";
                                ((Stoat) c).titleScale = 1F;
                                break;
                        }
                    }
                    if(c instanceof Stinkbug){
                        switch (AbstractDungeon.miscRng.random(3)){
                            case 0:
                                c.name = "OH...";
                                ((Stinkbug) c).titleScale = 1F;
                                break;
                            case 1:
                                c.name = "UM";
                                ((Stinkbug) c).titleScale = 1F;
                                break;
                            case 2:
                                c.name = "CHOOSE WISELY";
                                ((Stinkbug) c).titleScale = 0.8F;
                                break;
                            case 3:
                                c.name = "DO WHAT YOU MUST!";
                                ((Stinkbug) c).titleScale = 0.7F;
                                break;
                        }
                    }
                    if(c instanceof StuntedWolf){
                        switch (AbstractDungeon.miscRng.random(2)){
                            case 0:
                                c.name = "AHEM";
                                ((StuntedWolf) c).titleScale = 1F;
                                break;
                            case 1:
                                c.name = "IF YOU MUST";
                                ((StuntedWolf) c).titleScale = 1F;
                                break;
                            case 2:
                                c.name = "IT IS YOUR CHOICE";
                                ((StuntedWolf) c).titleScale = 0.7F;
                                break;
                        }
                    }
                }

                list.addToTop(c);
            }
        }
        return list;
    }



}
