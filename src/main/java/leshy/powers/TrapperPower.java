package leshy.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractPeltCard;
import leshy.powers.interfaces.CreatureValuePower;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class TrapperPower extends AbstractPower implements CloneablePowerInterface, CreatureValuePower {
    public AbstractCreature source;

    public static final String POWER_ID = LeshyMod.makeID(TrapperPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("trapper_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("trapper_power32.png"));

    public TrapperPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }



    @Override
    public void updateDescription() {

        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

    }


    @Override
    public AbstractPower makeCopy() {
        return new TrapperPower(owner, source, amount);
    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {
        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        if(c instanceof AbstractPeltCard){
            return amount;
        }
        return 0;
    }
}
