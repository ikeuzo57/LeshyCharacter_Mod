package leshy.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import java.util.ArrayList;

import static leshy.LeshyMod.makeCardPath;

public class BlackGoat extends AbstractCreatureCard {


    public static final String ID = LeshyMod.makeID(BlackGoat.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("BlackGoat.png");

    public static final ArrayList<String> IMG_PATHS = getImgPaths();

    private static ArrayList<String> getImgPaths(){
        ArrayList<String> paths = new ArrayList<>();
        paths.add(makeCardPath("BlackGoat.png"));
        paths.add(makeCardPath("BlackGoat_Sexy.png"));
        paths.add(makeCardPath("BlackGoat_Sigil.png"));
        paths.add(makeCardPath("BlackGoat_Sexy_Sigil.png"));
        return paths;
    }

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = Leshy.Enums.CREATURE;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;

    private static final int COST = -2;

    public BlackGoat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        sigilImgPath = makeCardPath("BlackGoat_Sigil.png");

        costType = CreatureCostType.BLOOD;
        extraCost = 1;

        tribe = CreatureTribe.HOOVED;

        attack = baseAttack = trueBaseAttack = 0;

        health = baseHealth = trueBaseHealth = 8;

        innate.add(Sigils.WORTHY_SACRIFICE);
        current.add(Sigils.WORTHY_SACRIFICE);

        spawnRate = 0.5F;

        mycologistReroll = 0.3F;

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
        if(AbstractDungeon.player instanceof Leshy && ((Leshy) AbstractDungeon.player).isSexy)
            index++;

        String path = IMG_PATHS.get(index);

        if(!path.equals(this.textureImg)){

            this.textureImg = path;
            loadCardImage(path);


        }

    }

}
