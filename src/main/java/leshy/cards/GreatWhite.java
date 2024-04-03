package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class GreatWhite extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(GreatWhite.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("GreatWhite.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public GreatWhite() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("GreatWhite_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 3;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 16;

        health = baseHealth = trueBaseHealth = 1;

        innate.add(Sigils.WATERBORNE);
        current.add(Sigils.WATERBORNE);

        fleeting = baseFleeting = true;

        initializeDescription();

    }

    @Override
    public String elderName() {
        return cardStrings.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
