package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

import static leshy.LeshyMod.makeCardPath;

public class BaitBucket extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(BaitBucket.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("BaitBucket.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public BaitBucket() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        tribe = CreatureTribe.NONE;

        health = baseHealth = trueBaseHealth = 6;

        fleeting = baseFleeting = true;
        bloodless = true;

        cardsToPreview = new GreatWhite();

        initializeDescription();
    }

    @Override
    public String extraText() {
        return cardStrings.EXTENDED_DESCRIPTION[0];
    }


    @Override
    public void onSacrifice() {
        super.onSacrifice();
        if(!bounce) {
            GreatWhite fish = new GreatWhite();
            fish.baseFleeting = true;
            addToBot(new SummonCreatureAction(new CreatureOrb(fish)));
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
