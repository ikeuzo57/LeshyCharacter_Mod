package leshy.helpers;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CallingBell;
import com.megacrit.cardcrawl.relics.TinyHouse;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import leshy.cards.*;
import leshy.events.*;
import leshy.relics.*;
import leshy.vfx.*;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.rewards.LinkedRewardItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DredgingPenalty {

    public enum Penalty {
        //Curses
        NORMALITY, PAIN, GLITCH, ROADKILL,
        //Blights
        INFESTATION, DISCRIMINATION, SCAVENGER, BEAR_TRAP,
        GRUDGE, DROWNED, STARVATION,
        //Misc
        HALF_MAX_HP, NONE
    }

    public static final HashMap<Penalty, String> penaltyStrings = makePenaltyMap();


    public static HashMap<Penalty, String> makePenaltyMap(){
        HashMap<Penalty, String> map = new HashMap<>();
        //Curses
        map.put(Penalty.NORMALITY, DredgingRoomEvent.DESCRIPTIONS[8]);
        map.put(Penalty.PAIN, DredgingRoomEvent.DESCRIPTIONS[9]);
        map.put(Penalty.ROADKILL, DredgingRoomEvent.DESCRIPTIONS[10]);
        //Blights
        map.put(Penalty.INFESTATION, DredgingRoomEvent.DESCRIPTIONS[11]);
        map.put(Penalty.DISCRIMINATION, DredgingRoomEvent.DESCRIPTIONS[12]);
        map.put(Penalty.SCAVENGER, DredgingRoomEvent.DESCRIPTIONS[13]);
        map.put(Penalty.BEAR_TRAP, DredgingRoomEvent.DESCRIPTIONS[14]);
        map.put(Penalty.GRUDGE, DredgingRoomEvent.DESCRIPTIONS[15]);
        map.put(Penalty.DROWNED, DredgingRoomEvent.DESCRIPTIONS[16]);
        map.put(Penalty.STARVATION, DredgingRoomEvent.DESCRIPTIONS[17]);
        //Misc
        map.put(Penalty.HALF_MAX_HP, DredgingRoomEvent.DESCRIPTIONS[18]);
        map.put(Penalty.GLITCH, DredgingRoomEvent.DESCRIPTIONS[19]);
        map.put(Penalty.NONE, "");
        return map;
    }


    private String text;
    private Penalty penalty;

    public AbstractCard previewCard = null;
    public AbstractRelic previewRelic = null;

    private int loseHP = -1;
    private AbstractCreatureCard.CreatureTribe change = null;

    public DredgingPenalty(Penalty penalty){
        this.text = makeText(penalty);
        this.penalty = penalty;
    }

    private String makeText(Penalty penalty){
        String text = penaltyStrings.get(penalty);

        switch (penalty){
            //Curses
            case NORMALITY:
                previewCard = new Normality();
                break;
            case PAIN:
                previewCard = new Pain();
                break;
            case ROADKILL:
                previewCard = new Roadkill();
                break;
            //Blights
            case INFESTATION:
                previewRelic = new InfestationRelic();
                break;
            case DISCRIMINATION:
                previewRelic = new DiscriminationRelic();
                break;
            case SCAVENGER:
                previewRelic = new ScavengerRelic();
                break;
            case BEAR_TRAP:
                previewRelic = new BearTrapRelic();
                break;
            case GRUDGE:
                previewRelic = new GrudgeRelic();
                break;
            case DROWNED:
                previewRelic = new DrownedRelic();
                break;
            case STARVATION:
                previewRelic = new StarvationRelic();
                break;
            //Misc
            case HALF_MAX_HP:
                loseHP = (int)(AbstractDungeon.player.maxHealth*0.4);
                text = text.replace("!HP!", ""+loseHP);
                break;
        }

        return text;
    }

    public String getText(){
        return text;
    }




    public void applyPenalty(){

        switch (this.penalty){
            //Curses
            case NORMALITY:
                for(int j=0; j<2; j++){
                    Normality n = new Normality();
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(n, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }
                break;
            case PAIN:
                for(int j=0; j<2; j++){
                    Pain pain = new Pain();
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(pain, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }
                break;
            case ROADKILL:
                for(int j=0; j<2; j++){
                    Roadkill roadkill = new Roadkill();
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(roadkill, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }
                break;

            //Blights
            case INFESTATION:
                InfestationRelic inf = new InfestationRelic();
                inf.instantObtain();
                break;
            case DISCRIMINATION:
                DiscriminationRelic dis = new DiscriminationRelic();
                dis.instantObtain();
                break;
            case SCAVENGER:
                ScavengerRelic sca = new ScavengerRelic();
                sca.instantObtain();
                break;
            case BEAR_TRAP:
                BearTrapRelic bear = new BearTrapRelic();
                bear.instantObtain();
                break;
            case GRUDGE:
                GrudgeRelic gru = new GrudgeRelic();
                gru.instantObtain();
                break;
            case DROWNED:
                DrownedRelic dro = new DrownedRelic();
                dro.instantObtain();
                break;
            case STARVATION:
                StarvationRelic star = new StarvationRelic();
                star.instantObtain();
                break;

            //Misc
            case HALF_MAX_HP:
                AbstractDungeon.player.decreaseMaxHealth(this.loseHP);
                break;
            case GLITCH:
                for(AbstractRelic r : AbstractDungeon.player.relics){
                    if(r instanceof OldDataRelic){
                        r.counter++;
                        r.updateDescription(AbstractDungeon.player.chosenClass);
                        r.flash();
                        break;
                    }
                }
                break;
        }

    }

    public static ArrayList<DredgingPenalty> getRandomPenalties(){
        ArrayList<Penalty> list = new ArrayList<>(Arrays.asList(Penalty.values()));
        list.remove(Penalty.NONE);
        for(AbstractRelic r : AbstractDungeon.player.relics){
            if(r instanceof InfestationRelic)
                list.remove(Penalty.INFESTATION);
            if(r instanceof DiscriminationRelic)
                list.remove(Penalty.DISCRIMINATION);
            if(r instanceof ScavengerRelic)
                list.remove(Penalty.SCAVENGER);
            if(r instanceof BearTrapRelic)
                list.remove(Penalty.BEAR_TRAP);
            if(r instanceof GrudgeRelic)
                list.remove(Penalty.GRUDGE);
            if(r instanceof DrownedRelic)
                list.remove(Penalty.DROWNED);
            if(r instanceof StarvationRelic)
                list.remove(Penalty.STARVATION);
        }
        ArrayList<Penalty> penalties = new ArrayList<>();
        while(penalties.size() < 4){
            penalties.add(list.remove(AbstractDungeon.miscRng.random(list.size()-1)));
        }

        ArrayList<DredgingPenalty> options = new ArrayList<>();
        for(int i=0; i<4; i++){
            options.add(new DredgingPenalty(penalties.get(i)));
        }

        return options;
    }



}





