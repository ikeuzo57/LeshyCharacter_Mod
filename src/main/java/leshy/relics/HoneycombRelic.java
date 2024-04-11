package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import leshy.LeshyMod;
import leshy.cards.Bee;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class HoneycombRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(HoneycombRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("honeycomb_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("honeycomb_relic.png"));

    public boolean active = true;

    public HoneycombRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);

    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        active = true;
        grayscale = false;
        flash();
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (damageAmount > 0 && active) {
            flash();
            addToTop(new MakeTempCardInHandAction(new Bee()));
            active = false;
            grayscale = true;
        }
        return damageAmount;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
