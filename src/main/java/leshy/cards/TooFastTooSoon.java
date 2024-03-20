package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.LeshyMod;
import leshy.actions.CreatureSacrificeAction;
import leshy.actions.SummonCreatureAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

import static leshy.LeshyMod.makeCardPath;

public class TooFastTooSoon extends AbstractDynamicCard {


    public static final String ID = LeshyMod.makeID(TooFastTooSoon.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("TooFastTooSoon.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = 3;


    public TooFastTooSoon() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        exhaust = true;

        upgradeDescription = true;

        cardsToPreview = new Grizzly();

    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {

        double halfHP = (double) AbstractDungeon.player.maxHealth / 2;

        if(AbstractDungeon.player.currentHealth > halfHP)
            return false;

        return super.cardPlayable(m);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractOrb o : p.orbs){

            if(o instanceof CreatureOrb && !(((CreatureOrb) o).creatureCard instanceof Starvation)){
                addToTop(new CreatureSacrificeAction((CreatureOrb) o));
            }

            Grizzly bear = new Grizzly();
            bear.baseFleeting = true;
            bear.gained.add(AbstractCreatureCard.Sigils.MIGHTY_LEAP);
            bear.current.add(AbstractCreatureCard.Sigils.MIGHTY_LEAP);

            if(upgraded){
                bear.baseAttack += 4;
                bear.baseHealth += 4;
                bear.attack = bear.baseAttack;
                bear.health = bear.baseHealth;
            }

            addToBot(new SummonCreatureAction(new CreatureOrb(bear)));


        }
    }

}
