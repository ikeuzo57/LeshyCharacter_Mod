package leshy.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class TheMoon extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(TheMoon.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("TheMoon.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    private static final int executeRatio = 3;

    public TheMoon() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        costType = CreatureCostType.NONE;
        extraCost = 0;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 20;

        health = baseHealth = trueBaseHealth = 200;

        secondMagicNumber = baseSecondMagicNumber = baseAttack * executeRatio;

        bloodless = true;

        initializeDescription();

    }

    @Override
    public String extraText() {
        return "Attacks all enemies. Attacks execute under !leshy:SecondMagic! health. NL On death, Die.";
    }


    @Override
    public void onSacrifice() {
        super.onSacrifice();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new LoseHPAction(p, p, 99999));
    }

    @Override
    public void applyPowers() {

        super.applyPowers();

        secondMagicNumber = baseSecondMagicNumber = attack * executeRatio;

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        super.calculateCardDamage(mo);

        secondMagicNumber = baseSecondMagicNumber = attack * executeRatio;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
