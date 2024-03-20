package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.abstracts.AbstractDynamicCard;
import leshy.cards.abstracts.BoonCard;
import leshy.orbs.CreatureOrb;

import static leshy.LeshyMod.makeCardPath;

public class BoonOfTheForest extends AbstractDynamicCard implements BoonCard {


    public static final String ID = LeshyMod.makeID(BoonOfTheForest.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("BoonOfTheForest.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;


    public BoonOfTheForest() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);


    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }


    @Override
    public void applyBoon() {
        for(AbstractOrb o : AbstractDungeon.player.orbs){

            if(o instanceof EmptyOrbSlot){
                addToBot(new SummonCreatureAction(new CreatureOrb(new GrandFir())));
            }

        }
    }
}
