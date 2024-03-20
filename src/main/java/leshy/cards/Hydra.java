package leshy.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import java.util.ArrayList;
import java.util.List;

import static leshy.LeshyMod.makeCardPath;

public class Hydra extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Hydra.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Hydra.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public static final int HYDRA_BUFF = 5;

    public Hydra() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Hydra_Sigil.png");

        costType = CreatureCostType.BONE;
        extraCost = 1;

        tribe = CreatureTribe.AMALGAM;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 10;

        baseMagicNumber = magicNumber = HYDRA_BUFF;

        innate.add(Sigils.BIFURCATED);
        innate.add(Sigils.TRIFURCATED);
        current.addAll(innate);


        initializeDescription();

    }

    @Override
    public void applyPowers() {

        int realAttack = baseAttack;
        int realHealth = baseHealth;

        baseAttack += (HYDRA_BUFF * LeshyMod.fullSets);
        baseHealth += (HYDRA_BUFF * LeshyMod.fullSets);

        super.applyPowers();

        baseAttack = realAttack;
        baseHealth = realHealth;

        isAttackModified = (attack != baseAttack);
        isHealthModified = (health != baseHealth);

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        int realAttack = baseAttack;
        int realHealth = baseHealth;

        baseAttack += (HYDRA_BUFF * LeshyMod.fullSets);
        baseHealth += (HYDRA_BUFF * LeshyMod.fullSets);

        super.calculateCardDamage(mo);

        baseAttack = realAttack;
        baseHealth = realHealth;

        isAttackModified = (attack != baseAttack);
        isHealthModified = (health != baseHealth);

    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {

        TooltipInfo fullSets = new TooltipInfo("Full Set Tracker", CuriousEgg.getFullSetTooltip());
        ArrayList<TooltipInfo> list = new ArrayList<>();
        list.add(fullSets);

        return list;

    }

    @Override
    public String extraText() {
        return "Gains !M! Attack and Heath for each set of each tribe in your master deck.";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
