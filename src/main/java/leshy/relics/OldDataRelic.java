package leshy.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.actions.BloodToSquirrelAction;
import leshy.actions.GlitchedTipAction;
import leshy.actions.SeekTopAction;
import leshy.cards.*;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractOldDataCard;
import leshy.helpers.OldDataSavable;
import leshy.orbs.CreatureOrb;
import leshy.powers.TotemPower;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.util.TextureLoader;

import java.util.ArrayList;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class OldDataRelic extends CustomRelic implements OnApplyPowerRelic, ClickableRelic, CustomSavable<OldDataSavable>, CreatureValueRelic, PostBattleSubscriber, OnStartBattleSubscriber {

    public static final String ID = LeshyMod.makeID(OldDataRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("old_data_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("old_data_relic.png"));

    public static final int STATIC_BUFF_GAINED = 2;

    public static final ArrayList<String> glitchedNames = new ArrayList<>();

    public boolean canActivate = true;

    public boolean head = false;
    public boolean left = false;
    public boolean top = false;
    public boolean sacrifice = false;
    public int staticBuff = 0;

    public static boolean selectingEffect = false;
    public static boolean selectingClick = false;

    public OldDataRelic(){
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);

        this.counter = 1;

        BaseMod.subscribe(this);

    }

    @Override
    public String getUpdatedDescription(){

        String desc = "";

        if(head)
            desc += DESCRIPTIONS[0] + " NL ";
        if(left)
            desc += DESCRIPTIONS[1] + " NL ";
        if(top)
            desc += DESCRIPTIONS[2] + " NL ";
        if(sacrifice)
            desc += DESCRIPTIONS[5] + " NL ";
        if(staticBuff > 0)
            desc += DESCRIPTIONS[3] + staticBuff + DESCRIPTIONS[4] + " NL ";

        desc += DESCRIPTIONS[6] + this.counter + DESCRIPTIONS[7];

        return desc;

    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c){
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Glitched Cards", glitchedCardsText()));
    }

    public static String glitchedCardsText(){
        String text = "";
        for(String s : glitchedNames)
            text += s + ", ";
        if(text.isEmpty())
            text = "Cards Glitched at start of combat will be listed here.";
        else
            text = text.substring(0, text.length()-2);
        return text;
    }

    @Override
    public void atBattleStartPreDraw() {
        ArrayList<AbstractCreatureCard> list = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.player.drawPile.group){
            if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).isStatic && !((AbstractCreatureCard) c).baseFleeting)
                list.add((AbstractCreatureCard) c);
        }
        while(list.size() > this.counter){
            list.remove(AbstractDungeon.miscRng.random(list.size()-1));
        }
        for(AbstractCreatureCard c : list){
            c.isStatic = true;
            MultiCardPreview.clear(c);
            glitchedNames.add(c.name);
        }
        addToBot(new GlitchedTipAction(this));
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        glitchedNames.clear();
        updateDescription(AbstractDungeon.player.chosenClass);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        glitchedNames.clear();
        updateDescription(AbstractDungeon.player.chosenClass);
    }

    @Override
    public void onShuffle() {
        canActivate = true;
    }

    @Override
    public void atBattleStart() {
        canActivate = true;
    }

    @Override
    public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1){

        if(head && abstractPower instanceof TotemPower)
            ((TotemPower) abstractPower).maxTribes++;

        return true;

    }


    @Override
    public void onRightClick() {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){

            boolean topAvailable = false;
            boolean sacrificeAvailable = false;

            if(this.top && canActivate && AbstractDungeon.player.drawPile.size() > 1)
                topAvailable = true;
            if(this.sacrifice){
                for(AbstractOrb o : AbstractDungeon.player.orbs){
                    if(o instanceof CreatureOrb && !(((CreatureOrb) o).creatureCard instanceof Starvation) && ((CreatureOrb) o).creatureCard.costType == AbstractCreatureCard.CreatureCostType.BLOOD){
                        sacrificeAvailable = true;
                        break;
                    }
                }
            }

            if(topAvailable && sacrificeAvailable){
                selectingClick = true;
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                tmp.addToTop(new OldDataTop());
                tmp.addToTop(new OldDataSacrifice());
                AbstractDungeon.gridSelectScreen.open(tmp, 1, false, "Choose an effect to use.");
            }else if(topAvailable){
                flash();
                canActivate = false;
                addToBot(new SeekTopAction());
            }else if(sacrificeAvailable){
                flash();
                addToBot(new BloodToSquirrelAction());
            }


        }
    }

    public static void gainEffect(){
        OldDataRelic odr = null;
        for(AbstractRelic r : AbstractDungeon.player.relics){
            if(r instanceof OldDataRelic){
                odr = (OldDataRelic) r;
                break;
            }
        }
        if(odr == null){
            odr = new OldDataRelic();
            odr.instantObtain();
        }
        odr.counter++;
        odr.updateDescription(AbstractDungeon.player.chosenClass);

        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        if(!odr.head)
            tmp.addToTop(new OldDataHead());
        if(!odr.left)
            tmp.addToTop(new OldDataLeft());
        if(!odr.top)
            tmp.addToTop(new OldDataTop());
        if(!odr.sacrifice)
            tmp.addToTop(new OldDataSacrifice());
        tmp.addToTop(new OldDataStatic());

        selectingEffect = true;
        AbstractDungeon.gridSelectScreen.open(tmp, 1, false, "Choose an effect to add to OLD_DATA.");
    }

    @Override
    public void update() {
        super.update();

        if(selectingEffect && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1){
            selectingEffect = false;
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            if(card instanceof AbstractOldDataCard){
                ((AbstractOldDataCard) card).addEffect(this);
                updateDescription(AbstractDungeon.player.chosenClass);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
        }

        if(selectingClick && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1){
            selectingClick = false;
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            if(card instanceof OldDataTop){
                flash();
                canActivate = false;
                addToBot(new SeekTopAction());
            }else if(card instanceof OldDataSacrifice){
                flash();
                addToBot(new BloodToSquirrelAction());
            }
        }

    }

    @Override
    public OldDataSavable onSave() {
        OldDataSavable save = new OldDataSavable();
        save.head = this.head;
        save.left = this.left;
        save.top = this.top;
        save.sacrifice = this.sacrifice;
        save.staticBuff = this.staticBuff;
        return save;
    }

    @Override
    public void onLoad(OldDataSavable save) {
        this.head = save.head;
        this.left = save.left;
        this.top = save.top;
        this.sacrifice = save.sacrifice;
        this.staticBuff = save.staticBuff;
        updateDescription(AbstractDungeon.player.chosenClass);
    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {
        if(c.isStatic)
            return staticBuff;
        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        if(c.isStatic)
            return staticBuff;
        return 0;
    }
}
