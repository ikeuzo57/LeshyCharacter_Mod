package leshy.cards;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import leshy.LeshyMod;
import leshy.actions.KnifeAction;
import leshy.cards.abstracts.AbstractDynamicCard;

import static leshy.LeshyMod.makeCardPath;

public class Knife extends AbstractDynamicCard {


    public static final String ID = LeshyMod.makeID(Knife.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Knife.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -1;


    public Knife() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseDamage = 12;
        upDamage = 3;

        this.exhaust = true;

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

        addToBot(new KnifeAction(m, new DamageInfo(p, damage*effect, DamageInfo.DamageType.NORMAL)));
    }

}
