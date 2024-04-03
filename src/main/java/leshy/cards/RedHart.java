package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;
import leshy.powers.SacrificePower;

import static leshy.LeshyMod.makeCardPath;

public class RedHart extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(RedHart.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("RedHart.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    //private static final float SCALING = 0.5F;

    public RedHart() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("RedHart_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 2;

        tribe = CreatureTribe.HOOVED;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 16;

        innate.add(Sigils.RAMPAGER);
        current.add(Sigils.RAMPAGER);

        initializeDescription();

    }

    /*
    @Override
    public double specialAttackMultiplier() {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            int sacrifices = 0;
            for(AbstractPower ap : AbstractDungeon.player.powers){
                if(ap instanceof SacrificePower) {
                    sacrifices = ap.amount;
                    break;
                }
            }
            return (sacrifices * SCALING) + 1.0;
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
