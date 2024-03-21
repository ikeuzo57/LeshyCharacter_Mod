package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import java.util.ArrayList;

import static leshy.LeshyMod.makeCardPath;

public class MudTurtle extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(MudTurtle.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("MudTurtle.png");

    public static final ArrayList<String> IMG_PATHS = getImgPaths();

    private static ArrayList<String> getImgPaths(){
        ArrayList<String> paths = new ArrayList<>();
        paths.add(makeCardPath("MudTurtle.png"));
        paths.add(makeCardPath("MudTurtle_Shelless.png"));
        paths.add(makeCardPath("MudTurtle_Sigil.png"));
        paths.add(makeCardPath("MudTurtle_Shelless_Sigil.png"));
        return paths;
    }

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public MudTurtle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("MudTurtle_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 2;

        tribe = CreatureTribe.REPTILE;

        attack = baseAttack = trueBaseAttack = 4;

        health = baseHealth = trueBaseHealth = 12;

        innate.add(Sigils.ARMORED);
        current.add(Sigils.ARMORED);

        initializeDescription();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void setSigilImg() {

        int index = 0;
        if(!gained.isEmpty())
            index += 2;
        if(this.armoredTriggered)
            index++;

        String path = IMG_PATHS.get(index);

        if(!path.equals(this.textureImg)){

            this.textureImg = path;
            loadCardImage(path);


        }

    }

}
