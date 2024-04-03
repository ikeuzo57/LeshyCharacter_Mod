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

public class BellTentacle extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(BellTentacle.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("BellTentacle.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    //private static final float SCALING = 1.0F;
    private static final int BUFF = 12;

    public BellTentacle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("BellTentacle_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 2;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 12;

        health = baseHealth = trueBaseHealth = 4;


        initializeDescription();

    }

    @Override
    public void applyPowers() {

        int index = 0;
        if(this.orb != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            index = AbstractDungeon.player.orbs.indexOf(this.orb);


        int realBaseAttack = baseAttack;
        baseAttack += index*BUFF;

        super.applyPowers();

        baseAttack = realBaseAttack;
        isAttackModified = (baseAttack != attack);

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        int index = 0;
        if(this.orb != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            index = AbstractDungeon.player.orbs.indexOf(this.orb);


        int realBaseAttack = baseAttack;
        baseAttack += index*BUFF;

        super.calculateCardDamage(mo);

        baseAttack = realBaseAttack;
        isAttackModified = (baseAttack != attack);

    }

    /*
    @Override
    public double specialAttackMultiplier() {
        if(this.orb != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            int index = AbstractDungeon.player.orbs.indexOf(this.orb);
            if(index > 0)
                return (index * SCALING) + 1.0;
        }
        return 1.0;
    }
    */

    @Override
    public String extraText() {
        return cardStrings.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
