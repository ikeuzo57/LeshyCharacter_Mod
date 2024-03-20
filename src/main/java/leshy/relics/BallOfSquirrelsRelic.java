package leshy.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.LeshyMod;
import leshy.characters.Leshy;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BallOfSquirrelsRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(BallOfSquirrelsRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ball_of_squirrels_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ball_of_squirrels_relic.png"));

    public BallOfSquirrelsRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);

    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.masterHandSize -= 1;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.masterHandSize += 1;
    }

    @Override
    public void atTurnStart() {
        flash();
        for(int i=0; i<2; i++)
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(LeshyMod.getSquirrel()));
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

}
