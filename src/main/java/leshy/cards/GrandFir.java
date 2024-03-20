package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class GrandFir extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(GrandFir.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("GrandFir.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public GrandFir() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        costType = CreatureCostType.NONE;

        tribe = CreatureTribe.NONE;


        bloodless = true;
        fleeting = baseFleeting = true;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 8;

        secondMagicNumber = baseSecondMagicNumber = 2;

        innate.add(Sigils.MIGHTY_LEAP);
        current.add(Sigils.MIGHTY_LEAP);

        initializeDescription();

    }

    @Override
    public String extraText() {
        return "At end of turn gain !leshy:SecondMagic! Health.";
    }


    @Override
    public void onPassive() {
        super.onPassive();
        baseHealth += secondMagicNumber;
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
