package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import leshy.LeshyMod;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class RecruitmentPosterRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(RecruitmentPosterRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("recruitment_poster_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("recruitment_poster_relic.png"));


    public RecruitmentPosterRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);

    }

    @Override
    public void atBattleStartPreDraw() {
        addToTop(new MakeTempCardInDrawPileAction(LeshyMod.getSquirrel(), 3, true, true));
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
