package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class Reginald extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Reginald.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Reginald.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public Reginald() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        costType = CreatureCostType.BONE;
        extraCost = 3;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 6;

        health = baseHealth = trueBaseHealth = 16;

        innate.add(Sigils.TOUCH_OF_DEATH);
        current.addAll(innate);

        initializeDescription();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
