package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.BaitBucket;
import leshy.cards.GoldNugget;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class AnglerMaskRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(AnglerMaskRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("angler_mask_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("angler_mask_relic.png"));

    public AnglerMaskRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);

    }

    @Override
    public void atBattleStart() {
        addToBot(new SummonCreatureAction(new CreatureOrb(new BaitBucket())));
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
