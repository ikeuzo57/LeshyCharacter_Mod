package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.BlackGoat;
import leshy.cards.GoldNugget;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class ProspectorMaskRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(ProspectorMaskRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("prospector_mask_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("prospector_mask_relic.png"));

    public ProspectorMaskRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);

    }

    @Override
    public void atBattleStart() {
        addToBot(new SummonCreatureAction(new CreatureOrb(new GoldNugget())));
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
