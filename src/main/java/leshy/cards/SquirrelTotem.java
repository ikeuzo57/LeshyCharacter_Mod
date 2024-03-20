package leshy.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.TotemHeadAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.cards.abstracts.TotemHeadCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class SquirrelTotem extends AbstractDynamicCard implements TotemHeadCard {


    public static final String ID = LeshyMod.makeID(SquirrelTotem.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("SquirrelTotem.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = 1;


    public SquirrelTotem() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        upgradeDescription = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TotemHeadAction(this));
        addToBot(new DrawCardAction(1));
    }

    @Override
    public void upgrade() {
        isInnate = true;
        super.upgrade();
    }

    @Override
    public AbstractCreatureCard.CreatureTribe giveTribe() {
        return AbstractCreatureCard.CreatureTribe.SQUIRREL;
    }
}
