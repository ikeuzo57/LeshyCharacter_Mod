package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BloodBagRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(BloodBagRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("blood_bag_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("blood_bag_relic.png"));


    public static final int LIFE_LOSS = 5;

    public BloodBagRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);

    }


    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
