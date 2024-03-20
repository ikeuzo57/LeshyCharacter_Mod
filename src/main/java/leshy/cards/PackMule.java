package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.PackMuleAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class PackMule extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(PackMule.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("PackMule.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public PackMule() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("PackMule_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.HOOVED;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 2;


        initializeDescription();

    }

    @Override
    public void onSacrifice() {
        super.onSacrifice();
        addToBot(new PackMuleAction());
    }

    @Override
    public String extraText() {
        return "On death, pick a Creature from a leshy:Pack to add to hand.";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
