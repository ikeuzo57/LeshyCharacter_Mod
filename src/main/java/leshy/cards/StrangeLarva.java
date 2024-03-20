package leshy.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

public class StrangeLarva extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(StrangeLarva.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("StrangeLarva.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public int turns;

    public StrangeLarva() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.INSECT;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 1;

        innate.add(Sigils.FLEDGLING);
        current.add(Sigils.FLEDGLING);

        StrangePupa pupa = new StrangePupa();
        Mothman moth = new Mothman();
        MultiCardPreview.add(this, new AbstractCard[] { pupa, moth });

        initializeDescription();

    }

    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();
        StrangePupa pupa = new StrangePupa();
        pupa.transform(this);
        pupa.baseAttack += (this.baseAttack - this.trueBaseAttack);
        pupa.baseHealth += (this.baseHealth - this.trueBaseHealth);
        this.isTransforming = true;
        pupa.transformed = true;
        pupa.isFrail = this.isFrail;
        CreatureOrb orb = new CreatureOrb(pupa);
        orb.damageTaken = this.orb.damageTaken;
        addToBot(new CreatureSacrificeAction(this.orb));
        addToBot(new SummonCreatureAction(orb, AbstractDungeon.player.orbs.indexOf(this.orb)));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
