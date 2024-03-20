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

public class Mantis extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Mantis.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Mantis.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public Mantis() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Mantis_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.INSECT;

        attack = baseAttack = trueBaseAttack = 6;

        health = baseHealth = trueBaseHealth = 2;

        innate.add(Sigils.BIFURCATED);
        current.add(Sigils.BIFURCATED);

        initializeDescription();

    }


    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();

        if(current.contains(Sigils.FLEDGLING)){
            MantisGod god = new MantisGod();
            god.transform(this);
            god.baseAttack += (this.baseAttack - this.trueBaseAttack);
            god.baseHealth += (this.baseHealth - this.trueBaseHealth);
            god.gained.remove(Sigils.FLEDGLING);
            god.current.remove(Sigils.FLEDGLING);
            this.isTransforming = true;
            god.transformed = true;
            god.isFrail = this.isFrail;
            CreatureOrb orb = new CreatureOrb(god);
            orb.damageTaken = this.orb.damageTaken;
            addToBot(new CreatureSacrificeAction(this.orb));
            addToBot(new SummonCreatureAction(orb, AbstractDungeon.player.orbs.indexOf(this.orb)));
        }

    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        if(current != null && current.contains(Sigils.FLEDGLING))
            cardsToPreview = new MantisGod();
        else
            cardsToPreview = null;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
