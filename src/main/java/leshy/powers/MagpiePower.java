package leshy.powers;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.OnPlayerTurnStartPostDrawSubscriber;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.LeshyMod;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class MagpiePower extends AbstractPower implements CloneablePowerInterface, OnPlayerTurnStartSubscriber {
    public AbstractCreature source;

    public static final String POWER_ID = LeshyMod.makeID(MagpiePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("magpie_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("magpie_power32.png"));

    public MagpiePower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        name = NAME;
        ID = POWER_ID;

        BaseMod.subscribe(this);

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        this.type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }


    @Override
    public void updateDescription() {

        if(amount == 1)
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        else
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];

    }

    @Override
    public void receiveOnPlayerTurnStart() {
        addToBot(new BetterDrawPileToHandAction(amount));
    }

    @Override
    public void onRemove() {
        super.onRemove();
        BaseMod.unsubscribe(this);
    }

    @Override
    public void onVictory() {
        super.onVictory();
        BaseMod.unsubscribe(this);
    }

    @Override
    public AbstractPower makeCopy() {
        return new MagpiePower(owner, source, amount);
    }


}
