package leshy.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import leshy.LeshyMod;
import leshy.helpers.DredgingPenalty;
import leshy.helpers.DredgingReward;
import leshy.relics.OldDataRelic;

import java.util.ArrayList;

import static leshy.LeshyMod.makeEventPath;

public class DredgingRoomEvent extends AbstractImageEvent {


    public static final String ID = LeshyMod.makeID("DredgingRoomEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("DredgingRoomEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private ArrayList<DredgingPenalty> penalties;
    private ArrayList<DredgingReward> rewards;

    private static final ArrayList<String> bodyText = makeBodyText();

    private static ArrayList<String> makeBodyText(){
        ArrayList<String> list = new ArrayList<>();
        list.add(addSquiggles(DESCRIPTIONS[3]));
        list.add(addSquiggles(DESCRIPTIONS[4]));
        list.add(addSquiggles(DESCRIPTIONS[5]));
        list.add(addSquiggles(DESCRIPTIONS[6]));
        list.add(addSquiggles(DESCRIPTIONS[7]));
        return list;
    }

    public static String addSquiggles(String text){
        String[] tokens = text.split(" ");
        for(int i=0; i< tokens.length; i++)
            tokens[i] = "~" + tokens[i] + "~";
        return String.join(" ", tokens);
    }

    public DredgingRoomEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);


        this.imageEventText.setDialogOption(OPTIONS[0]);


        this.hasDialog = true;
        this.hasFocus = true;
        this.combatTime = false;

        this.noCardsInRewards = true;

        penalties = DredgingPenalty.getRandomPenalties();
        rewards = DredgingReward.getOptions();

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

                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                this.imageEventText.clearAllDialogs();
                OldDataRelic relic = new OldDataRelic();
                for(AbstractRelic r : AbstractDungeon.player.relics){
                    if(r instanceof OldDataRelic){
                        relic.counter = r.counter;
                        break;
                    }
                }
                relic.counter++;
                relic.updateDescription(AbstractDungeon.player.chosenClass);
                this.imageEventText.setDialogOption(OPTIONS[1], relic);
                this.imageEventText.setDialogOption(OPTIONS[2]);

                screenNum = 1;
                break;


            case 1:
                switch (i) {
                    case 0:

                        OldDataRelic.gainEffect();

                        this.imageEventText.updateBodyText(bodyText.get(AbstractDungeon.miscRng.random(bodyText.size()-1)));
                        this.imageEventText.clearAllDialogs();


                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.setDialogOption(OPTIONS[4]);

                        screenNum = 2;
                        break;

                    case 1:
                        openMap();
                        break;
                }
                break;

            case 2:
                switch(i){
                    case 0:
                        this.imageEventText.updateBodyText(bodyText.get(AbstractDungeon.miscRng.random(bodyText.size()-1)));
                        this.imageEventText.clearAllDialogs();

                        for(int j = 0; j< penalties.size(); j++){
                            DredgingPenalty o = penalties.get(j);
                            String preText = "[" + String.format("%2s", Integer.toBinaryString(j)).replace(' ', '0') + "] ";
                            if(o.previewCard != null)
                                this.imageEventText.setDialogOption(preText + o.getText(), o.previewCard);
                            else if(o.previewRelic != null)
                                this.imageEventText.setDialogOption(preText + o.getText(), o.previewRelic);
                            else
                                this.imageEventText.setDialogOption(preText + o.getText());
                        }

                        screenNum = 3;
                        break;

                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        screenNum = 10;
                        break;
                }
                break;

            case 3:

                penalties.get(i).applyPenalty();

                this.imageEventText.updateBodyText(bodyText.get(AbstractDungeon.miscRng.random(bodyText.size()-1)));
                this.imageEventText.clearAllDialogs();

                for(int j = 0; j< rewards.size(); j++){
                    DredgingReward o = rewards.get(j);
                    String preText = "[" + String.format("%2s", Integer.toBinaryString(j)).replace(' ', '0') + "] ";
                    if(o.previewCard != null)
                        this.imageEventText.setDialogOption(preText + o.getText(), o.previewCard);
                    else if(o.previewRelic != null)
                        this.imageEventText.setDialogOption(preText + o.getText(), o.previewRelic);
                    else
                        this.imageEventText.setDialogOption(preText + o.getText());
                }

                screenNum = 4;
                break;

            case 4: //OLD_DATA choices

                rewards.get(i).applyReward();

                if(rewards.get(i).isEvents()){
                    this.imageEventText.updateBodyText("");
                    chooseEvent();
                }else{
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[2]);
                    screenNum = 10;
                }
                break;

            case 10:
                openMap();
                break;

        }
    }

    public void chooseEvent(){
        rewards = new ArrayList<>();
        rewards.add(new DredgingReward(DredgingReward.Reward.BONE_ALTAR));
        rewards.add(new DredgingReward(DredgingReward.Reward.GOOBERT));
        rewards.add(new DredgingReward(DredgingReward.Reward.MYCOLOGISTS));
        rewards.add(new DredgingReward(DredgingReward.Reward.MYSTERIOUS_STONES));
        rewards.add(new DredgingReward(DredgingReward.Reward.PACK));
        rewards.add(new DredgingReward(DredgingReward.Reward.TRAPPER));
        rewards.add(new DredgingReward(DredgingReward.Reward.WOODCARVER));

        this.imageEventText.clearAllDialogs();
        for(int j = 0; j< rewards.size(); j++){
            DredgingReward o = rewards.get(j);
            String preText = "[" + String.format("%3s", Integer.toBinaryString(j)).replace(' ', '0') + "] ";
            if(o.previewCard != null)
                this.imageEventText.setDialogOption(preText + o.getText(), o.previewCard);
            else if(o.previewRelic != null)
                this.imageEventText.setDialogOption(preText + o.getText(), o.previewRelic);
            else
                this.imageEventText.setDialogOption(preText + o.getText());
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        this.imageEventText.render(sb);
    }



}
