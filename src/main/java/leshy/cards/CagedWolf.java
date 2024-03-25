package leshy.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

import static leshy.LeshyMod.makeCardPath;

public class CagedWolf extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(CagedWolf.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("CagedWolf.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;


    public CagedWolf() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        tribe = CreatureTribe.CANINE;

        costType = CreatureCostType.BLOOD;
        extraCost = 2;


        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 12;


        bloodless = true;
        fleeting = baseFleeting = true;

        cardsToPreview = new Wolf();

        initializeDescription();
    }

    @Override
    public String extraText() {
        return "On death, replace this with a Wolf permanently.";
    }


    @Override
    public void onSacrifice() {
        super.onSacrifice();
        if(!bounce) {
            AbstractCreatureCard wolf = new Wolf();
            wolf.transform(this);
            wolf.baseAttack += (this.baseAttack - this.trueBaseAttack);
            wolf.baseHealth += (this.baseHealth - this.trueBaseHealth);
            wolf.attack = wolf.baseAttack;
            wolf.health = wolf.baseHealth;
            wolf.initializeDescription();
            CreatureOrb orb = new CreatureOrb(wolf);
            addToBot(new SummonCreatureAction(orb));
        }
        AbstractCreatureCard purge = null;
        for(AbstractCard ac : AbstractDungeon.player.masterDeck.group){
            if(ac.uuid == this.uuid && ac instanceof AbstractCreatureCard){
                purge = (AbstractCreatureCard) ac;
                AbstractCreatureCard wolf = new Wolf();
                wolf.transform(purge);
                wolf.baseAttack += (purge.baseAttack - purge.trueBaseAttack);
                wolf.baseHealth += (purge.baseHealth - purge.trueBaseHealth);
                wolf.attack = wolf.baseAttack;
                wolf.health = wolf.baseHealth;
                wolf.initializeDescription();
                AbstractDungeon.player.masterDeck.addToTop(wolf);
                break;
            }
        }
        if(purge != null)
            AbstractDungeon.player.masterDeck.removeCard(purge);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
