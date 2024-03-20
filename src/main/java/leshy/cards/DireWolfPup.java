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

public class DireWolfPup extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(DireWolfPup.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("DireWolfPup.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public DireWolfPup() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("DireWolfPup_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 2;

        tribe = CreatureTribe.CANINE;


        attack = baseAttack = trueBaseAttack = 6;

        health = baseHealth = trueBaseHealth = 6;

        innate.add(Sigils.FLEDGLING);
        innate.add(Sigils.BONE_DIGGER);
        current.addAll(innate);

        cardsToPreview = new DireWolf();

        initializeDescription();

    }


    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();
        AbstractPlayer p = AbstractDungeon.player;
        DireWolf wolf = new DireWolf();
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
