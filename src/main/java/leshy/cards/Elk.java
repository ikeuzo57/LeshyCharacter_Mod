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

public class Elk extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Elk.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Elk.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public Elk() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Elk_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 2;

        tribe = CreatureTribe.HOOVED;

        attack = baseAttack = trueBaseAttack = 8;

        health = baseHealth = trueBaseHealth = 16;

        innate.add(Sigils.RAMPAGER);
        current.add(Sigils.RAMPAGER);

        initializeDescription();

    }

    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();

        if(current.contains(Sigils.FLEDGLING)){
            MooseBuck buck = new MooseBuck();
            buck.transform(this);
            buck.baseAttack += (this.baseAttack - this.trueBaseAttack);
            buck.baseHealth += (this.baseHealth - this.trueBaseHealth);
            buck.gained.remove(Sigils.FLEDGLING);
            buck.current.remove(Sigils.FLEDGLING);
            this.isTransforming = true;
            buck.transformed = true;
            buck.isFrail = this.isFrail;
            CreatureOrb orb = new CreatureOrb(buck);
            orb.damageTaken = this.orb.damageTaken;
            addToBot(new CreatureSacrificeAction(this.orb));
            addToBot(new SummonCreatureAction(orb, AbstractDungeon.player.orbs.indexOf(this.orb)));
        }

    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        if(current != null && current.contains(Sigils.FLEDGLING))
            cardsToPreview = new MooseBuck();
        else
            cardsToPreview = null;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
