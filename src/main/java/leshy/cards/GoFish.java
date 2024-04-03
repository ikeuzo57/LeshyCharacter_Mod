package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.GoFishAction;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class GoFish extends AbstractDynamicCard {


    public static final String ID = LeshyMod.makeID(GoFish.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("GoFish.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = 0;


    public GoFish() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);


        upgradeDescription = true;


    }

    @Override
    public void upgrade() {
        selfRetain = true;
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new GoFishAction());

    }

}
