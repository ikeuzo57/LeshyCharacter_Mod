package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.cards.abstracts.BoonCard;
import leshy.orbs.CreatureOrb;

import static leshy.LeshyMod.makeCardPath;

public class BoonOfGoatsBlood extends AbstractDynamicCard implements BoonCard {


    public static final String ID = LeshyMod.makeID(BoonOfGoatsBlood.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("BoonOfGoatsBlood.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;


    public BoonOfGoatsBlood() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);


    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }


    @Override
    public void applyBoon() {
        addToBot(new SummonCreatureAction(new CreatureOrb(new BlackGoat())));
    }
}
