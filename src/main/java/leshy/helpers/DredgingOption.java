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

public class DredgingOption {

    public enum Reward {
        //Relics
        THREE_COMMON, TWO_UNCOMMON, CHOICE_RARE, ONE_BOSS,
        //Events
        EVENTS, MYSTERIOUS_STONES, MYCOLOGISTS, GOOBERT, BONE_ALTAR,
        //Misc
        REMOVE_THREE_CREATURES, CHANGE_TRIBE, SKIN,
        SQUIRREL_MORPH, GLITCH_BUFF, DUPLICATE_DECK,
        BLOOD_TO_BONE, THREE_COLORLESS, DEATHCARD
    }

    public enum Penalty {
        //Curses
        NORMALITY, PAIN, GLITCH, ROADKILL,
        //Blights
        INFESTATION, DISCRIMINATION, SCAVENGER, BEAR_TRAP,
        GRUDGE, DROWNED, STARVATION,
        //Misc
        HALF_MAX_HP, NONE
    }

    public static final HashMap<Reward, String> rewardStrings = makeRewardMap();
    public static final HashMap<Penalty, String> penaltyStrings = makePenaltyMap();

    public static HashMap<Reward, String> makeRewardMap(){
        HashMap<Reward, String> map = new HashMap<>();
        //Relics
        map.put(Reward.THREE_COMMON, "#gGain #g3 #gCommon #gRelics.");
        map.put(Reward.TWO_UNCOMMON, "#gGain #g2 #gUncommon #gRelics.");
        map.put(Reward.CHOICE_RARE, "#gGain #g1 #gof #g3 #gRare #gRelics.");
        map.put(Reward.ONE_BOSS, "#gGain #ga #gBoss #gRelic.");
        //Events
        map.put(Reward.EVENTS, "#gChoose #gan #gEvent. #rObtain #ra #rCurse.");
        map.put(Reward.MYSTERIOUS_STONES, "#gMysterious #gStones.");
        map.put(Reward.MYCOLOGISTS, "#gMycologists.");
        map.put(Reward.GOOBERT, "#gGoobert.");
        map.put(Reward.BONE_ALTAR, "#gBone #gAltar.");
        //Misc
        map.put(Reward.REMOVE_THREE_CREATURES, "#gRemove #gthree #gcreatures.");
        map.put(Reward.CHANGE_TRIBE, "#gPick #ga #gcreature #gto #gbecome #ga #g!TRIBE!.");
        map.put(Reward.SQUIRREL_MORPH, "#gReplace #gtwo #gcreatures #gwith #gSquirrels.");
        map.put(Reward.GLITCH_BUFF, "#gGlitch #gtwo #gcreatures. #gUpgrade #gthem #gthrice.");
        map.put(Reward.DUPLICATE_DECK, "#gDuplicate #geach #gcreature #gin #gyour #gdeck.");
        map.put(Reward.BLOOD_TO_BONE, "#gChange #ga #gcreature's #gcost #gto #gBone.");
        map.put(Reward.THREE_COLORLESS, "#gAdd #gthree #gColorless #gcards #gto #gyour #gdeck.");
        map.put(Reward.SKIN, "#gReplace #gtwo #gcreatures #gwith #gPelts.");
        map.put(Reward.DEATHCARD, "#gAdd #ga #gDeathcard #gto #gyour #gdeck.");
        return map;
    }

    public static HashMap<Penalty, String> makePenaltyMap(){
        HashMap<Penalty, String> map = new HashMap<>();
        //Curses
        map.put(Penalty.NORMALITY, "#rBecome #rCursed #r- #r2 #rNormality.");
        map.put(Penalty.PAIN, "#rBecome #rCursed #r- #r2 #rPain.");
        map.put(Penalty.ROADKILL, "#rBecome #rCursed #r- #r2 #rRoadkill.");
        //Blights
        map.put(Penalty.INFESTATION, "#rGain #rInfestation.");
        map.put(Penalty.DISCRIMINATION, "#rGain #rDiscrimination.");
        map.put(Penalty.SCAVENGER, "#rGain #rScavenger.");
        map.put(Penalty.BEAR_TRAP, "#rGain #rBear #rTrap.");
        map.put(Penalty.GRUDGE, "#rGain #rGrudge.");
        map.put(Penalty.DROWNED, "#rGain #rDrowned.");
        map.put(Penalty.STARVATION, "#rGain #rStarvation.");
        //Misc
        map.put(Penalty.HALF_MAX_HP, "#rLose #r!HP! #rMax #rHP.");
        map.put(Penalty.GLITCH, "#rGlitch #ran #radditional #rcreature.");
        map.put(Penalty.NONE, "");
        return map;
    }


    private String text;
    private Reward reward;
    private Penalty penalty;

    public AbstractCard previewCard = null;
    public AbstractRelic previewRelic = null;

    private int loseHP = -1;
    private AbstractCreatureCard.CreatureTribe change = null;

    public DredgingOption(Reward reward, Penalty penalty){
        this.text = makeText(reward, penalty);
        this.reward = reward;
        this.penalty = penalty;
    }

    private String makeText(Reward reward, Penalty penalty){
        String text = rewardStrings.get(reward) + " " + penaltyStrings.get(penalty);

        switch (reward){
            case CHANGE_TRIBE:
                switch (AbstractDungeon.miscRng.random(4)){
                    case 0:
                        change = AbstractCreatureCard.CreatureTribe.AVIAN;
                        break;
                    case 1:
                        change = AbstractCreatureCard.CreatureTribe.CANINE;
                        break;
                    case 2:
                        change = AbstractCreatureCard.CreatureTribe.HOOVED;
                        break;
                    case 3:
                        change = AbstractCreatureCard.CreatureTribe.INSECT;
                        break;
                    case 4:
                        change = AbstractCreatureCard.CreatureTribe.REPTILE;
                        break;
                }
                text = text.replace("!TRIBE!", AbstractCreatureCard.tribeText(change));
                break;
        }

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
                loseHP = AbstractDungeon.player.maxHealth/2;
                text = text.replace("!HP!", ""+loseHP);
                break;
        }

        return text;
    }

    public String getText(){
        return text;
    }

    public boolean isEvents(){
        return reward == Reward.EVENTS;
    }

    public void applyOption(){
        applyReward();
        applyPenalty();
    }


    private void applyReward(){

        switch (this.reward){
            //Relics
            case THREE_COMMON:
                (AbstractDungeon.getCurrRoom()).rewards.clear();
                for(int j=0; j<3; j++)
                    AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON));
                (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();
                AbstractDungeon.getCurrRoom().event.hasFocus = false;
                break;
            case TWO_UNCOMMON:
                (AbstractDungeon.getCurrRoom()).rewards.clear();
                for(int j=0; j<2; j++)
                    AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.UNCOMMON));
                (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();
                AbstractDungeon.getCurrRoom().event.hasFocus = false;
                break;
            case CHOICE_RARE:
                (AbstractDungeon.getCurrRoom()).rewards.clear();
                LinkedRewardItem firstRare = new LinkedRewardItem(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE)));
                LinkedRewardItem secondRare = new LinkedRewardItem(firstRare, AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE));
                LinkedRewardItem thirdRare = new LinkedRewardItem(firstRare, AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE));
                secondRare.addRelicLink(thirdRare);
                AbstractDungeon.getCurrRoom().rewards.add(firstRare);
                AbstractDungeon.getCurrRoom().rewards.add(secondRare);
                AbstractDungeon.getCurrRoom().rewards.add(thirdRare);
                (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();
                AbstractDungeon.getCurrRoom().event.hasFocus = false;
                break;
            case ONE_BOSS:
                AbstractRelic boss = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.BOSS);
                while(boss instanceof CallingBell || boss instanceof TinyHouse)
                    boss = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.BOSS);
                (AbstractDungeon.getCurrRoom()).rewards.clear();
                AbstractDungeon.getCurrRoom().addRelicToRewards(boss);
                (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();
                AbstractDungeon.getCurrRoom().event.hasFocus = false;
                break;

            //Events
            case MYSTERIOUS_STONES:
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
                AbstractDungeon.getCurrRoom().event = new MysteriousStonesEvent();
                AbstractDungeon.getCurrRoom().event.onEnterRoom();
                break;
            case MYCOLOGISTS:
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
                AbstractDungeon.getCurrRoom().event = new MycologistsEvent();
                AbstractDungeon.getCurrRoom().event.onEnterRoom();
                break;
            case GOOBERT:
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
                AbstractDungeon.getCurrRoom().event = new GoobertEvent();
                AbstractDungeon.getCurrRoom().event.onEnterRoom();
                break;
            case BONE_ALTAR:
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
                AbstractDungeon.getCurrRoom().event = new BoneAltarEvent();
                AbstractDungeon.getCurrRoom().event.onEnterRoom();
                break;
            case EVENTS:
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE), MathUtils.random(0.1F, 0.9F) * Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT));
                break;

            //Misc
            case REMOVE_THREE_CREATURES:
                AbstractDungeon.effectsQueue.add(new RemoveCreaturesEffect(3));
                break;
            case CHANGE_TRIBE:
                AbstractDungeon.effectsQueue.add(new ChangeTribeEffect(change));
                break;
            case SQUIRREL_MORPH:
                AbstractDungeon.effectsQueue.add(new ReplaceSquirrelEffect(2));
                break;
            case GLITCH_BUFF:
                AbstractDungeon.effectsQueue.add(new GlitchBuffEffect(2));
                break;
            case DUPLICATE_DECK:
                for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
                    if(c instanceof AbstractCreatureCard){
                        float x = MathUtils.random(0.1F, 0.9F) * Settings.WIDTH;
                        float y = MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT;
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeStatEquivalentCopy(), x, y));
                    }
                break;
            case BLOOD_TO_BONE:
                AbstractDungeon.effectsQueue.add(new BloodToBoneEffect());
                break;
            case THREE_COLORLESS:
                (AbstractDungeon.getCurrRoom()).rewards.clear();
                for(int i=0; i<3; i++)
                    AbstractDungeon.getCurrRoom().addCardReward(new RewardItem(AbstractCard.CardColor.COLORLESS));
                (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();
                AbstractDungeon.getCurrRoom().event.hasFocus = false;
                break;
            case SKIN:
                AbstractDungeon.effectsQueue.add(new ReplacePeltEffect(2));
                break;
            case DEATHCARD:
                (AbstractDungeon.getCurrRoom()).rewards.clear();
                RewardItem reward = new RewardItem(AbstractCard.CardColor.COLORLESS);
                reward.cards = getDeathcards(reward.cards.size());
                AbstractDungeon.getCurrRoom().addCardReward(reward);
                (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();
                AbstractDungeon.getCurrRoom().event.hasFocus = false;
                break;
        }

    }

    private void applyPenalty(){

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

    public static ArrayList<DredgingOption> getOptions(){

        ArrayList<Reward> rewards = new ArrayList<>();
        rewards.add(Reward.EVENTS);
        rewards.add(getRelicOption());
        rewards.addAll(getMiscOptions());

        ArrayList<Penalty> penalties = getRandomPenalties();

        ArrayList<DredgingOption> options = new ArrayList<>();
        for(int i=0; i<4; i++){
            options.add(new DredgingOption(rewards.get(i), penalties.get(i)));
        }
        return options;

    }

    public static Reward getRelicOption(){
        ArrayList<Reward> list = new ArrayList<>();
        list.add(Reward.THREE_COMMON);
        list.add(Reward.TWO_UNCOMMON);
        list.add(Reward.CHOICE_RARE);
        list.add(Reward.ONE_BOSS);
        return list.get(AbstractDungeon.miscRng.random(list.size()-1));
    }

    public static ArrayList<Reward> getMiscOptions(){
        ArrayList<Reward> list = new ArrayList<>();
        list.add(Reward.REMOVE_THREE_CREATURES);
        list.add(Reward.CHANGE_TRIBE);
        list.add(Reward.SQUIRREL_MORPH);
        list.add(Reward.GLITCH_BUFF);
        list.add(Reward.DUPLICATE_DECK);
        list.add(Reward.BLOOD_TO_BONE);
        list.add(Reward.THREE_COLORLESS);
        list.add(Reward.SKIN);
        list.add(Reward.DEATHCARD);
        while(list.size() > 2){
            list.remove(AbstractDungeon.miscRng.random(list.size()-1));
        }
        return list;
    }

    public static ArrayList<Penalty> getRandomPenalties(){
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
        return penalties;
    }

    public static ArrayList<AbstractCard> getDeathcards(int num){

        ArrayList<AbstractCard> options = new ArrayList<>();
        options.add(new Cody());
        options.add(new David());
        options.add(new Kaminski());
        options.add(new Kaycee());
        options.add(new Louis());
        options.add(new Reginald());
        options.add(new LukeCarder());
        options.add(new Oct19());
        options.add(new Sean());
        options.add(new Tamara());

        while(options.size() > num)
            options.remove(AbstractDungeon.cardRng.random(options.size()-1));

        return options;

    }

}





