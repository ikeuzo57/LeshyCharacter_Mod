package leshy.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.RightClickCard;
import leshy.characters.Leshy;


import java.util.ArrayList;
import java.util.List;

import static leshy.LeshyMod.makeCardPath;

public class CuriousEgg extends AbstractCreatureCard implements RightClickCard {


    public static final String ID = LeshyMod.makeID(CuriousEgg.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("CuriousEgg.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public CuriousEgg() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        costType = CreatureCostType.BONE;
        extraCost = 1;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 4;

        baseMagicNumber = magicNumber = 2;

        cardsToPreview = new Hydra();

        initializeDescription();

    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();

        if(LeshyMod.fullSets >= magicNumber){

            Hydra hydra = new Hydra();
            hydra.transform(this);
            hydra.baseAttack += (this.baseAttack - this.trueBaseAttack);
            hydra.baseHealth += (this.baseHealth - this.trueBaseHealth);
            addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
            addToBot(new MakeTempCardInHandAction(hydra));

        }

    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {

        TooltipInfo fullSets = new TooltipInfo("Full Set Tracker", getFullSetTooltip());
        ArrayList<TooltipInfo> list = new ArrayList<>();
        list.add(fullSets);

        return list;

    }

    public static String getFullSetTooltip(){

        if(!LeshyMod.fullSetUpdated)
            return "Updates on card gain, card removal, battle start, or right clicking this card.";

        String tooltip = "";

        tooltip += "Avian : " + LeshyMod.avian + ", ";
        tooltip += "Canine : " + LeshyMod.canine + ", ";
        tooltip += "Hooved : " + LeshyMod.hooved + ", ";
        tooltip += "Insect : " + LeshyMod.insect + ", ";
        tooltip += "Reptile : " + LeshyMod.reptile + ", ";
        tooltip += "Amalgam : " + LeshyMod.amalgam + ", ";
        tooltip += "Full Sets : " + LeshyMod.fullSets;

        return tooltip;
    }

    public void onRightClick(){

        LeshyMod.updateFullSets();

    }


    public void clickUpdate(){

        if ((HitboxRightClick.rightClicked.get(this.hb)).booleanValue())
            onRightClick();

    }

    @Override
    public String elderName() {
        return "Extra Curious Egg";
    }

    @Override
    public String extraText() {
        return "leshy:Finical_Hatchling";
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
