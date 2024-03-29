package leshy.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.SilentRemovePowerAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;
import leshy.powers.StuntedWolfPower;

import java.util.ArrayList;
import java.util.Arrays;

import static leshy.LeshyMod.makeCardPath;

public class StuntedWolf extends AbstractCreatureCard implements StartupCard {


    public static final String ID = LeshyMod.makeID(StuntedWolf.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("StuntedWolf.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    private Sigils sigil;

    private StuntedWolfPower wolfPower;

    public StuntedWolf() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("StuntedWolf_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.CANINE;

        attack = baseAttack = trueBaseAttack = 10;

        health = baseHealth = trueBaseHealth = 10;

        sigil = null;

        initializeDescription();

    }

    @Override
    public String extraText() {
        String keyword = "___";

        if(sigil != null)
            keyword = AbstractCreatureCard.keywordStrings.get(sigil);

        return "All creatures gain " + keyword + ". Changes randomly when drawn.";
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if(sigil != null)
            current.add(sigil);

        initializeDescription();

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        current.add(sigil);

        initializeDescription();

    }

    @Override
    public void onSummon() {
        super.onSummon();
        AbstractPlayer p = AbstractDungeon.player;
        if(sigil != null){
            wolfPower = new StuntedWolfPower(p, sigil);
            addToTop(new ApplyPowerAction(p, p, wolfPower));
        }

        switch (AbstractDungeon.miscRng.random(3)){
            case 0:
                name = "HERE WE ARE";
                titleScale = 1F;
                break;
            case 1:
                name = "ARE YOU SURE?";
                titleScale = 0.9F;
                break;
            case 2:
                name = "I MUST TRUST YOU";
                titleScale = 0.7F;
                break;
            case 3:
                name = "USE ME WISELY";
                titleScale = 0.7F;
                break;
        }

    }

    @Override
    public void onDamage() {
        super.onDamage();

        switch (AbstractDungeon.miscRng.random(2)){
            case 0:
                name = "CAREFUL NOW";
                titleScale = 0.9F;
                break;
            case 1:
                name = "OOF";
                titleScale = 1F;
                break;
            case 2:
                name = "GOOD LORD!";
                titleScale = 1F;
                break;
        }
    }

    @Override
    public void onSacrifice() {
        super.onSacrifice();
        if(wolfPower != null){
            addToBot(new SilentRemovePowerAction(wolfPower));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();

        ArrayList<Sigils> list = new ArrayList<>(Arrays.asList(AbstractCreatureCard.Sigils.values()));
        list.remove(Sigils.AMORPHOUS);
        list.remove(Sigils.TRINKET_BEARER);
        int size = list.size();
        int index = AbstractDungeon.miscRng.random(size - 1);

        sigil = list.get(index);

        initializeDescription();

        switch (AbstractDungeon.miscRng.random(2)){
            case 0:
                name = "WE MEET AGAIN";
                titleScale = 0.8F;
                break;
            case 1:
                name = "GREETINGS";
                titleScale = 1F;
                break;
            case 2:
                name = "HELLO AGAIN";
                titleScale = 0.9F;
                break;
        }

    }

    @Override
    public boolean atBattleStartPreDraw() {
        super.atBattleStartPreDraw();

        ArrayList<Sigils> list = new ArrayList<>(Arrays.asList(AbstractCreatureCard.Sigils.values()));
        list.remove(Sigils.AMORPHOUS);
        list.remove(Sigils.TRINKET_BEARER);
        int size = list.size();
        int index = AbstractDungeon.miscRng.random(size - 1);

        sigil = list.get(index);

        initializeDescription();

        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        if(c instanceof StuntedWolf)
            ((StuntedWolf) c).sigil = this.sigil;
        return c;
    }
}
