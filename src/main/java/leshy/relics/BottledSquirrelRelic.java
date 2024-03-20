package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.BaitBucket;
import leshy.cards.Smoke;
import leshy.cards.Squirrel;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BottledSquirrelRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(BottledSquirrelRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bottled_squirrel_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bottled_squirrel_relic.png"));

    public BottledSquirrelRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);

    }

    @Override
    public void atBattleStart() {
        addToTop(new MakeTempCardInHandAction(LeshyMod.getSquirrel()));
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
