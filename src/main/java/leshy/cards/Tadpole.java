package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.CreatureSacrificeAction;
import leshy.actions.SummonCreatureAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

import static leshy.LeshyMod.makeCardPath;

public class Tadpole extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Tadpole.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Tadpole.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public Tadpole() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Tadpole_Sigil.png");

        tribe = CreatureTribe.REPTILE;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 1;

        innate.add(Sigils.FLEDGLING);
        innate.add(Sigils.WATERBORNE);
        current.addAll(innate);

        cardsToPreview = new Bullfrog();

        initializeDescription();

    }

    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();
        Bullfrog frog = new Bullfrog();
        frog.transform(this);
        frog.baseAttack += (this.baseAttack - this.trueBaseAttack);
        frog.baseHealth += (this.baseHealth - this.trueBaseHealth);
        this.isTransforming = true;
        frog.transformed = true;
        frog.isFrail = this.isFrail;
        CreatureOrb orb = new CreatureOrb(frog);
        orb.damageTaken = this.orb.damageTaken;
        addToBot(new CreatureSacrificeAction(this.orb));
        addToBot(new SummonCreatureAction(orb, AbstractDungeon.player.orbs.indexOf(this.orb)));
    }


    @Override
    public void upgrade() {
        super.upgrade();
        cardsToPreview.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
