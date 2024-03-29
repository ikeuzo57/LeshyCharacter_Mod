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

public class Skink extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(Skink.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skink.png");

    public static final ArrayList<String> IMG_PATHS = getImgPaths();

    private static ArrayList<String> getImgPaths(){
        ArrayList<String> paths = new ArrayList<>();
        paths.add(makeCardPath("Skink.png"));
        paths.add(makeCardPath("Skink_Tailless.png"));
        paths.add(makeCardPath("Skink_Sigil.png"));
        paths.add(makeCardPath("Skink_Tailless_Sigil.png"));
        return paths;
    }

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public Skink() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("Skink_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.REPTILE;

        attack = baseAttack = trueBaseAttack = 6;

        health = baseHealth = trueBaseHealth = 6;

        innate.add(Sigils.LOOSE_TAIL);
        current.add(Sigils.LOOSE_TAIL);

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
        if(this.tailTriggered)
            index++;

        String path = IMG_PATHS.get(index);

        if(!path.equals(this.textureImg)){

            this.textureImg = path;
            loadCardImage(path);


        }

    }

}
