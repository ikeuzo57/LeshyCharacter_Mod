package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

import static leshy.LeshyMod.makeCardPath;

public class StrangeFrog extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(StrangeFrog.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("StrangeFrog.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public int x_value = -1;

    public StrangeFrog() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 1;

        bloodless = true;
        fleeting = baseFleeting = true;

        cardsToPreview = new LeapingTrap();

        initializeDescription();
    }

    @Override
    public void onSummon() {
        super.onSummon();
        if(x_value > 0){
            baseHealth = 3 * x_value;
        }else{
            x_value = 0;
            baseHealth = 1;
        }
        applyPowers();
    }

    @Override
    public String extraText() {

        String text = cardStrings.EXTENDED_DESCRIPTION[0];
        if(x_value == -1)
            text = cardStrings.EXTENDED_DESCRIPTION[1] + text;

        return text;

    }

    @Override
    public void onSacrifice() {
        super.onSacrifice();
        if(!bounce) {
            LeapingTrap trap = new LeapingTrap();
            trap.x_value = this.x_value;
            CreatureOrb orb = new CreatureOrb(trap);
            addToTop(new SummonCreatureAction(orb, 0));
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
