package leshy.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.FinicalHatchlingAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.RightClickCard;
import leshy.characters.Leshy;


import java.util.ArrayList;
import java.util.List;

import static leshy.LeshyMod.makeCardPath;

public class CuriousEgg extends AbstractCreatureCard implements RightClickCard, StartupCard {


    public static final String ID = LeshyMod.makeID(CuriousEgg.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

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
    public boolean atBattleStartPreDraw() {
        super.atBattleStartPreDraw();

        addToTop(new FinicalHatchlingAction(this, magicNumber));

        return false;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {

        TooltipInfo fullSets = new TooltipInfo(cardStrings.EXTENDED_DESCRIPTION[4], getFullSetTooltip());
        ArrayList<TooltipInfo> list = new ArrayList<>();
        list.add(fullSets);

        return list;

    }

    public static String getFullSetTooltip(){

        if(!LeshyMod.fullSetUpdated)
            return cardStrings.EXTENDED_DESCRIPTION[2];

        String tooltip = "";

        tooltip += AbstractCreatureCard.tribeText(CreatureTribe.AVIAN) + " : " + LeshyMod.avian + ", ";
        tooltip += AbstractCreatureCard.tribeText(CreatureTribe.CANINE) + " : " + LeshyMod.canine + ", ";
        tooltip += AbstractCreatureCard.tribeText(CreatureTribe.HOOVED) + " : " + LeshyMod.hooved + ", ";
        tooltip += AbstractCreatureCard.tribeText(CreatureTribe.INSECT) + " : " + LeshyMod.insect + ", ";
        tooltip += AbstractCreatureCard.tribeText(CreatureTribe.REPTILE) + " : " + LeshyMod.reptile + ", ";
        tooltip += AbstractCreatureCard.tribeText(CreatureTribe.AMALGAM) + " : " + LeshyMod.amalgam + ", ";
        tooltip += cardStrings.EXTENDED_DESCRIPTION[3] + " : " + LeshyMod.fullSets;

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
        return cardStrings.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public String extraText() {
        return cardStrings.EXTENDED_DESCRIPTION[1];
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
