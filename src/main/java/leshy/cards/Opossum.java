package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class Opossum extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Opossum.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Opossum.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public Opossum() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Opossum_Sigil.png");

        costType = CreatureCostType.BONE;
        extraCost = 2;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 5;

        health = baseHealth = trueBaseHealth = 5;

        initializeDescription();

    }

    @Override
    public String elderName() {
        return "Awesome Opossum";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
