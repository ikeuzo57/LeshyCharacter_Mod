package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.SkeletonCrew;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class LimoncelloRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(LimoncelloRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("limoncello_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("limoncello_relic.png"));

    public LimoncelloRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);

        this.counter = 6;

    }

    @Override
    public void atBattleStart() {
        this.counter = 6;
        this.grayscale = false;
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (damageAmount > 0 && this.counter > 0) {
            flash();
            this.counter--;
            if(this.counter <= 0)
                this.grayscale = true;
            addToTop(new SummonCreatureAction(new CreatureOrb(new SkeletonCrew())));
            return damageAmount;
        }
        return damageAmount;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
