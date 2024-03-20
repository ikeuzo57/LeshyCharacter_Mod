package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.KinAction;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class TrialOfKin extends AbstractDynamicCard {


    public static final String ID = LeshyMod.makeID(TrialOfKin.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("TrialOfKin.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = 2;


    public TrialOfKin() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        exhaust = true;

        upgradeDescription = true;

    }

    @Override
    public void upgrade() {
        super.upgrade();
        exhaust = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new KinAction());

    }

}
