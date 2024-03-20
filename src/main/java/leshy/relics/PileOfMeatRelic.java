package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class PileOfMeatRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(PileOfMeatRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("pile_of_meat_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("pile_of_meat_relic.png"));

    public PileOfMeatRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
