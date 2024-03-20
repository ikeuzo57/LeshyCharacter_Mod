package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.RarityAction;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class TrialOfRarity extends AbstractDynamicCard {


    public static final String ID = LeshyMod.makeID(TrialOfRarity.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("TrialOfRarity.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = 2;


    public TrialOfRarity() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        exhaust = true;

        upgradeDescription = true;

    }

    @Override
    public void upgrade() {
        super.upgrade();
        isInnate = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new RarityAction());

    }

}
