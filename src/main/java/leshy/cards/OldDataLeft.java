package leshy.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractOldDataCard;
import leshy.relics.OldDataRelic;

public class OldDataLeft extends AbstractOldDataCard {


    public static final String ID = LeshyMod.makeID(OldDataLeft.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;


    public OldDataLeft() {
        super(ID, AbstractOldDataCard.IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }


    @Override
    public void addEffect(OldDataRelic od) {
        od.left = true;
    }
}
