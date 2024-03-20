package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class HandTentacle extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(HandTentacle.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("HandTentacle.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    //private static final float SCALING = 0.2F;
    private static final int BUFF = 3;

    public HandTentacle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("HandTentacle_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 2;


        initializeDescription();

    }

    @Override
    public void applyPowers() {

        int realBaseAttack = baseAttack;
        baseAttack += AbstractDungeon.player.hand.size()*BUFF;

        super.applyPowers();

        baseAttack = realBaseAttack;
        isAttackModified = (baseAttack != attack);

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        int realBaseAttack = baseAttack;
        baseAttack += AbstractDungeon.player.hand.size()*BUFF;

        super.calculateCardDamage(mo);

        baseAttack = realBaseAttack;
        isAttackModified = (baseAttack != attack);

    }

    /*
    @Override
    public double specialAttackMultiplier() {
        if(AbstractDungeon.player.hand != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            return (AbstractDungeon.player.hand.size() * SCALING) + 1.0;
        }
        return 1.0;
    }
    */


    @Override
    public String extraText() {
        return "leshy:Card_Counter";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
