package leshy.powers;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.LeshyMod;
import leshy.cards.RedHart;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.powers.interfaces.CreatureValuePower;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.util.TextureLoader;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;

import static leshy.LeshyMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class SacrificePower extends AbstractPower implements CloneablePowerInterface, OnPlayerTurnStartSubscriber, InvisiblePower, CreatureValuePower {
    public AbstractCreature source;

    public static final String POWER_ID = LeshyMod.makeID(SacrificePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("bone_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("bone_power32.png"));

    private static final int BUFF = 6;

    public SacrificePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {

        BaseMod.subscribe(this);

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



    public void onSacrifice(){
        this.amount += 1;
    }

    @Override
    public AbstractPower makeCopy() {
        return new SacrificePower(owner, source, amount);
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        this.amount = 0;
    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {
        if(c instanceof RedHart)
            return this.amount*BUFF;
        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        return 0;
    }
}
