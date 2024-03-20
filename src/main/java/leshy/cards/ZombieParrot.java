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

public class ZombieParrot extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(ZombieParrot.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("ZombieParrot.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public ZombieParrot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("ZombieParrot_Sigil.png");

        costType = CreatureCostType.NONE;

        tribe = CreatureTribe.AVIAN;

        attack = baseAttack = trueBaseAttack = 18;

        health = baseHealth = trueBaseHealth = 3;

        innate.add(Sigils.AIRBORNE);
        current.add(Sigils.AIRBORNE);

        initializeDescription();

    }

    @Override
    public String extraText() {
        return "leshy:Brittle";
    }

    @Override
    public void onPassive() {
        super.onPassive();
        addToBot(new CreatureSacrificeAction(this.orb));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
