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

public class WolfCub extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(WolfCub.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("WolfCub.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public WolfCub() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("WolfCub_Sigil.png");

        tribe = CreatureTribe.CANINE;

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        attack = baseAttack = trueBaseAttack = 6;

        health = baseHealth = trueBaseHealth = 2;

        innate.add(Sigils.FLEDGLING);
        current.add(Sigils.FLEDGLING);

        cardsToPreview = new Wolf();

        initializeDescription();

    }

    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();
        Wolf wolf = new Wolf();
        wolf.transform(this);
        wolf.baseAttack += (this.baseAttack - this.trueBaseAttack);
        wolf.baseHealth += (this.baseHealth - this.trueBaseHealth);
        this.isTransforming = true;
        wolf.transformed = true;
        wolf.isFrail = this.isFrail;
        CreatureOrb orb = new CreatureOrb(wolf);
        orb.damageTaken = this.orb.damageTaken;
        addToBot(new CreatureSacrificeAction(this.orb));
        addToBot(new SummonCreatureAction(orb, AbstractDungeon.player.orbs.indexOf(this.orb)));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
