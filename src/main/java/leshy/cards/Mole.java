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

public class Mole extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Mole.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Mole.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;


    public Mole() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Mole_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.NONE;


        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 12;

        innate.add(Sigils.GUARDIAN);
        current.add(Sigils.GUARDIAN);

        initializeDescription();

    }

    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();

        if(current.contains(Sigils.FLEDGLING)){
            MoleMan man = new MoleMan();
            man.transform(this);
            man.baseAttack += (this.baseAttack - this.trueBaseAttack);
            man.baseHealth += (this.baseHealth - this.trueBaseHealth);
            man.gained.remove(Sigils.FLEDGLING);
            man.current.remove(Sigils.FLEDGLING);
            this.isTransforming = true;
            man.transformed = true;
            man.isFrail = this.isFrail;
            CreatureOrb orb = new CreatureOrb(man);
            orb.damageTaken = this.orb.damageTaken;
            addToBot(new CreatureSacrificeAction(this.orb));
            addToBot(new SummonCreatureAction(orb, AbstractDungeon.player.orbs.indexOf(this.orb)));
        }

    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        if(current != null && current.contains(Sigils.FLEDGLING))
            cardsToPreview = new MoleMan();
        else
            cardsToPreview = null;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
