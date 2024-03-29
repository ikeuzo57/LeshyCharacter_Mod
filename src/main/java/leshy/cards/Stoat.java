package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class Stoat extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Stoat.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Stoat.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public Stoat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Stoat_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.NONE;

        attack = baseAttack = trueBaseAttack = 3;

        health = baseHealth = trueBaseHealth = 9;

        initializeDescription();

    }


    @Override
    public void onSummon() {
        super.onSummon();

        switch (AbstractDungeon.miscRng.random(8)){
            case 0:
                name = "HERE WE GO";
                titleScale = 1F;
                break;
            case 1:
                name = "BACK ON THE BOARD";
                titleScale = 0.7F;
                break;
            case 2:
                name = "OK...";
                titleScale = 1F;
                break;
            case 3:
                name = "FINE";
                titleScale = 1F;
                break;
            case 4:
                name = "BAD PLAY";
                titleScale = 1F;
                break;
            case 5:
                name = "YOU SURE?";
                titleScale = 1F;
                break;
            case 6:
                name = "TOTAL MISPLAY";
                titleScale = 0.8F;
                break;
            case 7:
                name = "KEEP ME ALIVE";
                titleScale = 0.8F;
                break;
            case 8:
                name = "YEP";
                titleScale = 1F;
                break;
        }

    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();

        switch (AbstractDungeon.miscRng.random(3)){
            case 0:
                name = "LUCKY DRAW";
                titleScale = 1F;
                break;
            case 1:
                name = "NICE TOPDECK";
                titleScale = 0.8F;
                break;
            case 2:
                name = "ME AGAIN";
                titleScale = 1F;
                break;
            case 3:
                name = "YEP";
                titleScale = 1F;
                break;
        }

    }

    @Override
    public void onDamage() {
        super.onDamage();

        switch (AbstractDungeon.miscRng.random(4)){
            case 0:
                name = "ACK";
                titleScale = 1F;
                break;
            case 1:
                name = "STOP THAT";
                titleScale = 1F;
                break;
            case 2:
                name = "OW";
                titleScale = 1F;
                break;
            case 3:
                name = "OUCH";
                titleScale = 1F;
                break;
            case 4:
                name = "ENOUGH!";
                titleScale = 1F;
                break;
        }

    }

    @Override
    public String extraText() {
        return "leshy:Pick_Me";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

}
