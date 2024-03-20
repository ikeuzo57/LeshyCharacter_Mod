package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.CreatureSacrificeAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class SkeletonCrew extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(SkeletonCrew.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("SkeletonCrew.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public SkeletonCrew() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("SkeletonCrew_Sigil.png");

        costType = CreatureCostType.NONE;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 16;

        health = baseHealth = trueBaseHealth = 4;


        initializeDescription();

    }

    @Override
    public String extraText() {
        return "leshy:Brittle";
    }

    @Override
    public void onPassive() {
        super.onPassive();
        addToTop(new CreatureSacrificeAction(this.orb));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
