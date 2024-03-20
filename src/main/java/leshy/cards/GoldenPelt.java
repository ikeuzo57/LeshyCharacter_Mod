package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractPeltCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class GoldenPelt extends AbstractPeltCard {


    public static final String ID = LeshyMod.makeID(GoldenPelt.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("GoldenPelt.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public GoldenPelt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 6;

        secondMagicNumber = baseSecondMagicNumber = 130;
        upSecondMagicNumber = 30;

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
