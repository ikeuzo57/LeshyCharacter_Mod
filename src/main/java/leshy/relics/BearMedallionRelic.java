package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import leshy.LeshyMod;
import leshy.cards.Grizzly;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BearMedallionRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(BearMedallionRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bear_medallion_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bear_medallion_relic.png"));


    public BearMedallionRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);

    }

    @Override
    public void atTurnStartPostDraw() {
        Grizzly bear = new Grizzly();
        bear.isEthereal = true;
        bear.fleeting = bear.baseFleeting = true;
        addToBot(new MakeTempCardInHandAction(bear));
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
