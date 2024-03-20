package leshy.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.actions.GlitchAction;
import leshy.cards.abstracts.AbstractDynamicCard;

import java.util.ArrayList;
import java.util.List;

import static leshy.LeshyMod.makeCardPath;

public class Glitch extends AbstractDynamicCard implements StartupCard {


    public static final String ID = LeshyMod.makeID(Glitch.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Glitch.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = -2;



    public Glitch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }

    @Override
    public boolean atBattleStartPreDraw() {

        addToTop(new GlitchAction(this));

        return false;
    }


    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
