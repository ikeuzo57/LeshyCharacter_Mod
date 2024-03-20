package leshy.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

import static leshy.LeshyMod.makeCardPath;

public class StrangeFrogs extends AbstractDynamicCard {


    public static final String ID = LeshyMod.makeID(StrangeFrogs.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("StrangeFrog.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -1;


    public StrangeFrogs() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = 2;

        upgradeDescription = true;

        this.exhaust = true;

        StrangeFrog frog = new StrangeFrog();
        LeapingTrap trap = new LeapingTrap();
        MultiCardPreview.add(this, new AbstractCard[] { frog, trap });

    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.exhaust = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        if (!this.freeToPlayOnce)
            p.energy.use(EnergyPanel.totalCount);

        for(int i=0; i<magicNumber; i++){

            StrangeFrog frog = new StrangeFrog();
            frog.x_value = effect;
            addToBot(new SummonCreatureAction(new CreatureOrb(frog)));

        }
    }

}
