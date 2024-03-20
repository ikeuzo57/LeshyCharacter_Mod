package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.RoadkillAction;
import leshy.cards.abstracts.AbstractDynamicCard;

import static leshy.LeshyMod.makeCardPath;

public class Roadkill extends AbstractDynamicCard {


    public static final String ID = LeshyMod.makeID(Roadkill.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Roadkill.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = -2;


    public Roadkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new RoadkillAction(this));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
