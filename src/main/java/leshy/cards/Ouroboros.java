package leshy.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class Ouroboros extends AbstractCreatureCard implements SpawnModificationCard {


    public static final String ID = LeshyMod.makeID(Ouroboros.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Ouroboros.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public int deaths = 0;

    public Ouroboros() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Ouroboros_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 2;

        tribe = CreatureTribe.REPTILE;

        attack = baseAttack = trueBaseAttack = 8;

        health = baseHealth = trueBaseHealth = 8;

        innate.add(Sigils.UNKILLABLE);
        current.add(Sigils.UNKILLABLE);

        mycologistReroll = 0.01F;
        spawnRate = 0.3F;

        initializeDescription();

    }



    @Override
    public void onSacrifice() {
        super.onSacrifice();
        if(!bounce) {
            this.deaths++;
        }
    }

    @Override
    public double specialAttackMultiplier() {
        return 1 + (0.1D * this.deaths);
    }

    @Override
    public double specialHealthMultiplier() {
        return 1 + (0.1D * this.deaths);
    }

    @Override
    public String extraText() {
        return "leshy:Ouroboros";
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        if(card instanceof Ouroboros)
            ((Ouroboros) card).deaths = this.deaths;
        return card;
    }
}
