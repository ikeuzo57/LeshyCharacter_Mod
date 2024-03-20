package leshy.events.deprecated;

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

import java.util.ArrayList;

import static leshy.LeshyMod.makeEventPath;

public class RecruitmentEvent extends AbstractImageEvent {


    public static final String ID = LeshyMod.makeID("RecruitmentEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("CardRewardEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private boolean selectCreature = false;

    public RecruitmentEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);


        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
        this.imageEventText.setDialogOption(OPTIONS[3]);


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
                    case 0: //1 Blood
                        selectCreature = true;
                        AbstractDungeon.cardRewardScreen.customCombatOpen(getOptions(AbstractCreatureCard.CreatureCostType.BLOOD, 1, null), "Add one to your deck.", false);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[9]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;

                    case 1: //2 Blood
                        selectCreature = true;
                        AbstractDungeon.cardRewardScreen.customCombatOpen(getOptions(AbstractCreatureCard.CreatureCostType.BLOOD, 2, null), "Add one to your deck.", false);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[9]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;

                    case 2: //Bone
                        selectCreature = true;
                        AbstractDungeon.cardRewardScreen.customCombatOpen(getOptions(AbstractCreatureCard.CreatureCostType.BONE, -1, null), "Add one to your deck.", false);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[9]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;

                    case 3:
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.updateDialogOption(1, OPTIONS[5]);
                        this.imageEventText.updateDialogOption(2, OPTIONS[6]);
                        this.imageEventText.updateDialogOption(3, OPTIONS[7]);
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        screenNum = 1;
                        break;
                }
                break;

            case 1:
                switch (i){
                    case 0:
                        selectCreature = true;
                        AbstractDungeon.cardRewardScreen.customCombatOpen(getOptions(null, -1, AbstractCreatureCard.CreatureTribe.AVIAN), "Add one to your deck.", false);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[9]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;

                    case 1:
                        selectCreature = true;
                        AbstractDungeon.cardRewardScreen.customCombatOpen(getOptions(null, -1, AbstractCreatureCard.CreatureTribe.CANINE), "Add one to your deck.", false);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[9]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;

                    case 2:
                        selectCreature = true;
                        AbstractDungeon.cardRewardScreen.customCombatOpen(getOptions(null, -1, AbstractCreatureCard.CreatureTribe.HOOVED), "Add one to your deck.", false);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[9]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;

                    case 3:
                        selectCreature = true;
                        AbstractDungeon.cardRewardScreen.customCombatOpen(getOptions(null, -1, AbstractCreatureCard.CreatureTribe.INSECT), "Add one to your deck.", false);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[9]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;

                    case 4:
                        selectCreature = true;
                        AbstractDungeon.cardRewardScreen.customCombatOpen(getOptions(null, -1, AbstractCreatureCard.CreatureTribe.REPTILE), "Add one to your deck.", false);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[9]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;
                }
                break;

            case 2:
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

        if (AbstractDungeon.cardRewardScreen.discoveryCard != null && selectCreature) {
            selectCreature = false;
            AbstractCard c = AbstractDungeon.cardRewardScreen.discoveryCard.makeCopy();
            c.current_x = -1000.0F * Settings.xScale;
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.cardRewardScreen.discoveryCard = null;
        }


    }



    public static ArrayList<AbstractCard> getOptions(AbstractCreatureCard.CreatureCostType type, int cost, AbstractCreatureCard.CreatureTribe tribe){

        int count = 3;
        if (AbstractDungeon.ascensionLevel >= 15)
            count = 2;

        ArrayList<AbstractCard> commonList = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.srcCommonCardPool.group)
            if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).bloodless
                    && (type == null || type == ((AbstractCreatureCard) c).costType)
                    && (cost == -1 || cost == ((AbstractCreatureCard) c).extraCost)
                    && (tribe == null || tribe == ((AbstractCreatureCard) c).tribe
                    || (tribe == AbstractCreatureCard.CreatureTribe.INSECT && ((AbstractCreatureCard) c).tribe == AbstractCreatureCard.CreatureTribe.ANT)
                    || ((AbstractCreatureCard) c).tribe == AbstractCreatureCard.CreatureTribe.AMALGAM))
                commonList.add(c);
        ArrayList<AbstractCard> uncommonList = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.srcUncommonCardPool.group)
            if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).bloodless
                    && (type == null || type == ((AbstractCreatureCard) c).costType)
                    && (cost == -1 || cost == ((AbstractCreatureCard) c).extraCost)
                    && (tribe == null || tribe == ((AbstractCreatureCard) c).tribe
                    || (tribe == AbstractCreatureCard.CreatureTribe.INSECT && ((AbstractCreatureCard) c).tribe == AbstractCreatureCard.CreatureTribe.ANT)
                    || ((AbstractCreatureCard) c).tribe == AbstractCreatureCard.CreatureTribe.AMALGAM))
                uncommonList.add(c);
        ArrayList<AbstractCard> rareList = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.srcRareCardPool.group)
            if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).bloodless
                    && (type == null || type == ((AbstractCreatureCard) c).costType)
                    && (cost == -1 || cost == ((AbstractCreatureCard) c).extraCost)
                    && (tribe == null || tribe == ((AbstractCreatureCard) c).tribe
                    || (tribe == AbstractCreatureCard.CreatureTribe.INSECT && ((AbstractCreatureCard) c).tribe == AbstractCreatureCard.CreatureTribe.ANT)
                    || ((AbstractCreatureCard) c).tribe == AbstractCreatureCard.CreatureTribe.AMALGAM))
                rareList.add(c);

        LeshyMod.logger.info("Common List : " + commonList.size());
        LeshyMod.logger.info("Uncommon List : " + uncommonList.size());
        LeshyMod.logger.info("Rare List : " + rareList.size());

        ArrayList<AbstractCard> tmp = new ArrayList<>();

        while(tmp.size() < count){
            float roll = AbstractDungeon.miscRng.random();
            LeshyMod.logger.info("Roll : " + roll);
            AbstractCard card = null;
            if(roll < 0.05){
                if(!rareList.isEmpty()){
                    card = rareList.remove(AbstractDungeon.miscRng.random(rareList.size()-1));
                }else{
                    LeshyMod.logger.info("Common List Empty");
                }
            }else if(roll < 0.5){
                if(!uncommonList.isEmpty()){
                    card = uncommonList.remove(AbstractDungeon.miscRng.random(uncommonList.size()-1));
                }else{
                    LeshyMod.logger.info("Uncommon List Empty");
                }
            }else{
                if(!commonList.isEmpty()){
                    card = commonList.remove(AbstractDungeon.miscRng.random(commonList.size()-1));
                }else{
                    LeshyMod.logger.info("Rare List Empty");
                }
            }

            if(card == null)
                continue;

            LeshyMod.logger.info(card.name);
            tmp.add(card);

        }

        return tmp;
    }



}
