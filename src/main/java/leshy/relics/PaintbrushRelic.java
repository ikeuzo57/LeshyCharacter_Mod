package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.relics.interfaces.CreatureSigilRelic;
import leshy.util.TextureLoader;

import java.util.HashSet;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class PaintbrushRelic extends CustomRelic {

    public static final String ID = LeshyMod.makeID(PaintbrushRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("paintbrush_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("paintbrush_relic.png"));

    public PaintbrushRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

}
