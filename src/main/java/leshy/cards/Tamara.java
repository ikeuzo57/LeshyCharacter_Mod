package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class Tamara extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Tamara.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Tamara.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public Tamara() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        costType = CreatureCostType.BLOOD;
        extraCost = 2;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 12;

        health = baseHealth = trueBaseHealth = 18;

        innate.add(Sigils.AIRBORNE);
        innate.add(Sigils.LOOSE_TAIL);
        current.addAll(innate);

        initializeDescription();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
