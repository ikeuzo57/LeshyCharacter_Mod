package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.LeshyMod;
import leshy.actions.StruckGoldAction;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

import static leshy.LeshyMod.makeCardPath;

public class StruckGold extends AbstractDynamicCard {


    public static final String ID = LeshyMod.makeID(StruckGold.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("StruckGold.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = 1;


    public StruckGold() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        cardsToPreview = new GoldNugget();

        exhaust = true;

        upgradeDescription = true;

    }

    @Override
    public void upgrade() {
        exhaust = false;
        super.upgrade();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        for(AbstractOrb o : p.orbs){
            if(o instanceof CreatureOrb  && !(((CreatureOrb) o).creatureCard instanceof Starvation)){
                return super.canUse(p, m);
            }
        }
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new StruckGoldAction());
    }

}
