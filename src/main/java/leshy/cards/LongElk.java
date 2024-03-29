package leshy.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class LongElk extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(LongElk.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("LongElk.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public LongElk() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        costType = CreatureCostType.BONE;
        extraCost = 4;

        tribe = CreatureTribe.HOOVED;

        attack = baseAttack = trueBaseAttack = 6;

        health = baseHealth = trueBaseHealth = 14;

        innate.add(Sigils.RAMPAGER);
        innate.add(Sigils.TOUCH_OF_DEATH);
        current.addAll(innate);

        cardsToPreview = new Vertebrae();

        initializeDescription();

    }

    @Override
    public String elderName() {
        return "Longer Elk";
    }

    @Override
    public void onPassive() {
        super.onPassive();
        addToBot(new MakeTempCardInHandAction(new Vertebrae()));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public String extraText() {
        return "At end of turn, add a Vertebrae to hand.";
    }
}
