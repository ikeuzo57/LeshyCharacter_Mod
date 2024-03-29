package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

import static leshy.LeshyMod.makeCardPath;

public class MirrorTentacle extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(MirrorTentacle.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("MirrorTentacle.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public MirrorTentacle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("MirrorTentacle.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 6;


        initializeDescription();

    }

    @Override
    public String extraText() {
        return "leshy:M!rror_r0rriM";
    }


    @Override
    public void applyPowers() {

        int realBaseValue = baseAttack;

        if(this.orb != null){
            for(int i=0; i<AbstractDungeon.player.orbs.size(); i++){
                AbstractOrb o = AbstractDungeon.player.orbs.get(i);
                if(o == this.orb){
                    if(i > 0){
                        AbstractOrb orb = AbstractDungeon.player.orbs.get(i-1);
                        if(orb instanceof CreatureOrb){
                            baseAttack = ((CreatureOrb) orb).creatureCard.attack;
                        }
                    }
                    break;
                }
            }
        }

        super.applyPowers();

        baseAttack = realBaseValue;
        isAttackModified = (attack != baseAttack);

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        int realBaseValue = baseAttack;

        if(this.orb != null){
            for(int i=0; i<AbstractDungeon.player.orbs.size(); i++){
                AbstractOrb o = AbstractDungeon.player.orbs.get(i);
                if(o == this.orb){
                    if(i > 0){
                        AbstractOrb orb = AbstractDungeon.player.orbs.get(i-1);
                        if(orb instanceof CreatureOrb){
                            baseAttack += ((CreatureOrb) orb).creatureCard.attack;
                        }
                    }
                    break;
                }
            }
        }

        super.calculateCardDamage(mo);

        baseAttack = realBaseValue;
        isAttackModified = (attack != baseAttack);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
