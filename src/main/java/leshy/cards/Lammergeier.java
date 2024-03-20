package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;
import leshy.powers.BonePower;

import static leshy.LeshyMod.makeCardPath;

public class Lammergeier extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Lammergeier.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Lammergeier.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public Lammergeier() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Lammergeier_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 3;

        tribe = CreatureTribe.AVIAN;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 20;


        magicNumber = baseMagicNumber = 2;

        innate.add(Sigils.AIRBORNE);
        current.add(Sigils.AIRBORNE);

        initializeDescription();

    }

    @Override
    public void applyPowers() {

        int realBaseValue = baseAttack;

        for(AbstractPower ap : AbstractDungeon.player.powers){
            if(ap instanceof BonePower){
                baseAttack += ap.amount * magicNumber;
                break;
            }
        }

        super.applyPowers();

        baseAttack = realBaseValue;

        isAttackModified = (attack != baseAttack);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        int realBaseValue = baseAttack;

        for(AbstractPower ap : AbstractDungeon.player.powers){
            if(ap instanceof BonePower){
                baseAttack += ap.amount * magicNumber;
                break;
            }
        }

        super.calculateCardDamage(mo);

        baseAttack = realBaseValue;

        isAttackModified = (attack != baseAttack);

    }

    @Override
    public String extraText() {
        return "leshy:Double_Bones";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
