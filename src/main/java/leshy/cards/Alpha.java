package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class Alpha extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Alpha.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Alpha.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;


    public Alpha() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Alpha_Sigil.png");

        costType = CreatureCostType.BONE;
        extraCost = 4;

        tribe = CreatureTribe.CANINE;

        attack = baseAttack = trueBaseAttack = 8;

        health = baseHealth = trueBaseHealth = 10;

        innate.add(Sigils.LEADER);
        current.add(Sigils.LEADER);

        initializeDescription();

    }

    @Override
    public String elderName() {
        return "Omega";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
