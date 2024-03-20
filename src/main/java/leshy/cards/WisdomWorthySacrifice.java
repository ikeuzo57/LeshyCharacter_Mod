package leshy.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractWisdomCard;

import static leshy.LeshyMod.makeCardPath;

public class WisdomWorthySacrifice extends AbstractWisdomCard {


    public static final String ID = LeshyMod.makeID(WisdomWorthySacrifice.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("BloodBagTotem.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;


    public WisdomWorthySacrifice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        wisdom = AbstractCreatureCard.Sigils.WORTHY_SACRIFICE;

    }

}
