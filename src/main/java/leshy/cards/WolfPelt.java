package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractPeltCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class WolfPelt extends AbstractPeltCard {


    public static final String ID = LeshyMod.makeID(WolfPelt.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("WolfPelt.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public WolfPelt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 4;

        secondMagicNumber = baseSecondMagicNumber = 60;
        upSecondMagicNumber = 20;

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
