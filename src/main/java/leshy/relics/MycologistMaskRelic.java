package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class MycologistMaskRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(MycologistMaskRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("mycologist_mask_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("mycologist_mask_relic.png"));

    public MycologistMaskRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);

    }


    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
