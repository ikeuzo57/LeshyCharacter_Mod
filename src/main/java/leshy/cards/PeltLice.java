package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class PeltLice extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(PeltLice.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("PeltLice.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public PeltLice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("PeltLice_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 4;

        tribe = CreatureTribe.INSECT;

        attack = baseAttack = trueBaseAttack = 10;

        health = baseHealth = trueBaseHealth = 10;

        innate.add(Sigils.DOUBLE_STRIKE);
        current.add(Sigils.DOUBLE_STRIKE);

        fleeting = baseFleeting = true;
        isEthereal = true;


        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
