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

public class StrangePupa extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(StrangePupa.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("StrangePupa.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public StrangePupa() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.INSECT;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 1;

        innate.add(Sigils.FLEDGLING);
        current.add(Sigils.FLEDGLING);

        initializeDescription();

        cardsToPreview = new Mothman();

    }



    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();
        Mothman moth = new Mothman();
        moth.transform(this);
        moth.baseAttack += (this.baseAttack - this.trueBaseAttack);
        moth.baseHealth += (this.baseHealth - this.trueBaseHealth);
        this.isTransforming = true;
        moth.transformed = true;
        moth.isFrail = this.isFrail;
        CreatureOrb orb = new CreatureOrb(moth);
        orb.damageTaken = this.orb.damageTaken;
        addToBot(new CreatureSacrificeAction(this.orb));
        addToBot(new SummonCreatureAction(orb, AbstractDungeon.player.orbs.indexOf(this.orb)));
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
