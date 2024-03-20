package leshy.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class DeathCard extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(DeathCard.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("DeathCard.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public DeathCard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        costType = CreatureCostType.NONE;

        tribe = CreatureTribe.NONE;

        initializeDescription();

    }

    @Override
    public AbstractCard makeCopy() {
        DeathCard card = (DeathCard) super.makeCopy();
        card.cost = this.cost;
        card.costType = this.costType;
        card.extraCost = this.extraCost;
        card.baseAttack = card.attack = this.baseAttack;
        card.baseHealth = card.health = this.baseHealth;
        card.trueBaseAttack = this.trueBaseAttack;
        card.trueBaseHealth = this.trueBaseHealth;
        card.gained.addAll(this.gained);
        card.current.addAll(this.gained);
        return card;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
