package leshy.cards;

import com.megacrit.cardcrawl.actions.unique.SkewerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.TargetAction;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class Scissors extends AbstractDynamicCard {


    public static final String ID = LeshyMod.makeID(Scissors.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Scissors.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -1;


    public Scissors() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseDamage = 10;
        upDamage = 3;

        this.exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SkewerAction(p, m, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
    }

}
