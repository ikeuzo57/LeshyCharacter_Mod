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
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.events.*;
import leshy.relics.*;
import leshy.rewards.LinkedRewardItem;
import leshy.vfx.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DredgingReward {

    public enum Reward {
        //Relics
        THREE_COMMON, TWO_UNCOMMON, CHOICE_RARE, ONE_BOSS,
        //Events
        EVENTS, MYSTERIOUS_STONES, MYCOLOGISTS, GOOBERT, BONE_ALTAR, WOODCARVER, TRAPPER, PACK,
        //Misc
        REMOVE_THREE_CREATURES, CHANGE_TRIBE, SKIN,
        SQUIRREL_MORPH, GLITCH_BUFF, DUPLICATE_DECK,
        BLOOD_TO_BONE, THREE_COLORLESS, DEATHCARD
    }

    public static final HashMap<Reward, String> rewardStrings = makeRewardMap();

    public static HashMap<Reward, String> makeRewardMap(){
        HashMap<Reward, String> map = new HashMap<>();
        //Relics
        map.put(Reward.THREE_COMMON, DredgingRoomEvent.DESCRIPTIONS[20]);
        map.put(Reward.TWO_UNCOMMON, DredgingRoomEvent.DESCRIPTIONS[21]);
        map.put(Reward.CHOICE_RARE, DredgingRoomEvent.DESCRIPTIONS[22]);
        map.put(Reward.ONE_BOSS, DredgingRoomEvent.DESCRIPTIONS[23]);
        //Events
        map.put(Reward.EVENTS, DredgingRoomEvent.DESCRIPTIONS[24]);
        map.put(Reward.MYSTERIOUS_STONES, DredgingRoomEvent.DESCRIPTIONS[25]);
        map.put(Reward.MYCOLOGISTS, DredgingRoomEvent.DESCRIPTIONS[26]);
        map.put(Reward.GOOBERT, DredgingRoomEvent.DESCRIPTIONS[27]);
        map.put(Reward.BONE_ALTAR, DredgingRoomEvent.DESCRIPTIONS[28]);
        map.put(Reward.WOODCARVER, DredgingRoomEvent.DESCRIPTIONS[29]);
        map.put(Reward.TRAPPER, DredgingRoomEvent.DESCRIPTIONS[30]);
        map.put(Reward.PACK, DredgingRoomEvent.DESCRIPTIONS[31]);
        //Misc
        map.put(Reward.REMOVE_THREE_CREATURES, DredgingRoomEvent.DESCRIPTIONS[32]);
        map.put(Reward.CHANGE_TRIBE, DredgingRoomEvent.DESCRIPTIONS[33]);
        map.put(Reward.SQUIRREL_MORPH, DredgingRoomEvent.DESCRIPTIONS[34]);
        map.put(Reward.GLITCH_BUFF, DredgingRoomEvent.DESCRIPTIONS[35]);
        map.put(Reward.DUPLICATE_DECK, DredgingRoomEvent.DESCRIPTIONS[36]);
        map.put(Reward.BLOOD_TO_BONE, DredgingRoomEvent.DESCRIPTIONS[37]);
        map.put(Reward.THREE_COLORLESS, DredgingRoomEvent.DESCRIPTIONS[38]);
        map.put(Reward.SKIN, DredgingRoomEvent.DESCRIPTIONS[39]);
        map.put(Reward.DEATHCARD, DredgingRoomEvent.DESCRIPTIONS[40]);
        return map;
    }



    private String text;
    private Reward reward;

    public AbstractCard previewCard = null;
    public AbstractRelic previewRelic = null;

    private int loseHP = -1;
    private AbstractCreatureCard.CreatureTribe change = null;

    public DredgingReward(Reward reward){
        this.text = makeText(reward);
        this.reward = reward;
    }

    private String makeText(Reward reward){
        String text = rewardStrings.get(reward);

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

        return text;
    }

    public String getText(){
        return text;
    }

    public boolean isEvents(){
        return reward == Reward.EVENTS;
    }


    public void applyReward(){

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
            case WOODCARVER:
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
                AbstractDungeon.getCurrRoom().event = new WoodcarverEvent();
                AbstractDungeon.getCurrRoom().event.onEnterRoom();
                break;
            case TRAPPER:
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
                AbstractDungeon.getCurrRoom().event = new TrapperEvent();
                AbstractDungeon.getCurrRoom().event.onEnterRoom();
                break;
            case PACK:
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
                AbstractDungeon.getCurrRoom().event = new PackEvent();
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

    public static ArrayList<DredgingReward> getOptions(){

        ArrayList<Reward> rewards = new ArrayList<>();
        rewards.add(Reward.EVENTS);
        rewards.add(getRelicOption());
        rewards.addAll(getMiscOptions());

        ArrayList<DredgingReward> options = new ArrayList<>();
        for(int i=0; i<4; i++){
            options.add(new DredgingReward(rewards.get(i)));
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
        if(hasBlood())
            list.add(Reward.BLOOD_TO_BONE);
        list.add(Reward.THREE_COLORLESS);
        list.add(Reward.SKIN);
        list.add(Reward.DEATHCARD);
        while(list.size() > 2){
            list.remove(AbstractDungeon.miscRng.random(list.size()-1));
        }
        return list;
    }

    public static boolean hasBlood(){
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if(c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).costType == AbstractCreatureCard.CreatureCostType.BLOOD)
                return true;
        return false;
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





