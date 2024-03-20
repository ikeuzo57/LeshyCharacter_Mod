package leshy.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractTotemBaseCard;
import leshy.characters.Leshy;

import static leshy.LeshyMod.makeCardPath;

public class HoarderTotem extends AbstractTotemBaseCard {


    public static final String ID = LeshyMod.makeID(HoarderTotem.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("SeekTotem.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Leshy.Enums.LESHY_BROWN;


    public HoarderTotem() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        spawnRate = 0.2F;
        base = AbstractCreatureCard.Sigils.HOARDER;

    }

}
