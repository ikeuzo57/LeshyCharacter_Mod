package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class GhostFoodRelic extends CustomRelic implements CreatureValueRelic {

    public static final String ID = LeshyMod.makeID(GhostFoodRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ghost_food_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ghost_food_relic.png"));

    public GhostFoodRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);

    }

    private static final int BUFF = 2;

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {
        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        if(c.fleeting && !c.current.contains(AbstractCreatureCard.Sigils.UNKILLABLE))
            return BUFF;
        return 0;
    }
}
