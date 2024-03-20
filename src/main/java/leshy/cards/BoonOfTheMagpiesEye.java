package leshy.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.cards.abstracts.BoonCard;
import leshy.powers.MagpiePower;

import static leshy.LeshyMod.makeCardPath;

public class BoonOfTheMagpiesEye extends AbstractDynamicCard implements BoonCard {


    public static final String ID = LeshyMod.makeID(BoonOfTheMagpiesEye.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("BoonOfTheMagpiesEye.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;


    public BoonOfTheMagpiesEye() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);


    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }


    @Override
    public void applyBoon() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new DrawPower(p, -1), -1));
        addToBot(new ApplyPowerAction(p, p, new MagpiePower(p, p, 1), 1));
    }
}
