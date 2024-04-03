package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class DireWolf extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(DireWolf.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("DireWolf.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public DireWolf() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("DireWolf_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 3;

        tribe = CreatureTribe.CANINE;

        attack = baseAttack = trueBaseAttack = 16;

        health = baseHealth = trueBaseHealth = 30;


        innate.add(Sigils.DOUBLE_STRIKE);
        current.add(Sigils.DOUBLE_STRIKE);

        initializeDescription();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
