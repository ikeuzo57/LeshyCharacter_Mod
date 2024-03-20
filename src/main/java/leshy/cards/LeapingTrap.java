package leshy.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class LeapingTrap extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(LeapingTrap.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("LeapingTrap.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public int x_value = -1;

    public LeapingTrap() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 1;


        secondMagicNumber = baseSecondMagicNumber = 0;

        fleeting = baseFleeting = true;
        bloodless = true;

        initializeDescription();
    }

    @Override
    public void onSummon() {
        super.onSummon();
        if(x_value > 0){
            secondMagicNumber = baseSecondMagicNumber = 4 * x_value;
        }else{
            x_value = 0;
            secondMagicNumber = baseSecondMagicNumber = 0;
        }
        applyPowers();
    }

    @Override
    public String extraText() {
        if(x_value == -1)
            return "On death deal 4X to all enemies.";
        return "On death deal !leshy:SecondMagic! to all enemies.";
    }

    @Override
    public void onSacrifice() {
        super.onSacrifice();
        if(!bounce) {
            addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, secondMagicNumber, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
