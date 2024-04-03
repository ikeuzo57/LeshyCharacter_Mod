package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class FieldMice extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(FieldMice.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("FieldMice.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public FieldMice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("FieldMice_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 2;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 14;

        health = baseHealth = trueBaseHealth = 14;

        innate.add(Sigils.FECUNDITY);
        current.add(Sigils.FECUNDITY);

        mycologistReroll = 0.3F;

        initializeDescription();

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
