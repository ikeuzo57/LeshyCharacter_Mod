package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.actions.HoneycombAction;
import leshy.actions.SummonCreatureAction;
import leshy.cards.SkeletonCrew;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class HoneycombRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(HoneycombRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("honeycomb_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("honeycomb_relic.png"));

    public HoneycombRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);

    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (damageAmount > 0) {
            flash();
            addToTop(new HoneycombAction());
        }
        return damageAmount;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
