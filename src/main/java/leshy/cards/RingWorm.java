package leshy.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;
import leshy.relics.DeadSurvivorsRelic;

import java.util.ArrayList;

import static leshy.LeshyMod.makeCardPath;

public class RingWorm extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(RingWorm.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("RingWorm.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;


    public RingWorm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("RingWorm_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.INSECT;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 1;

        mycologistReroll = 0.01F;

        initializeDescription();

    }

    @Override
    public String extraText() {
        return cardStrings.EXTENDED_DESCRIPTION[0];
    }


    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {

        for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if(c.cardID.equals(this.cardID))
                return false;

        for(AbstractRelic r : AbstractDungeon.player.relics)
            if(r instanceof DeadSurvivorsRelic)
                return false;

        return super.canSpawn(currentRewardCards);

    }

    @Override
    public boolean canSpawnShop(ArrayList<AbstractCard> currentShopCards) {

        for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if(c.cardID.equals(this.cardID))
                return false;

        for(AbstractRelic r : AbstractDungeon.player.relics)
            if(r instanceof DeadSurvivorsRelic)
                return false;

        return super.canSpawn(currentShopCards);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
