package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class TombstoneRelic extends CustomRelic implements CreatureValueRelic {

    public static final String ID = LeshyMod.makeID(TombstoneRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tombstone_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tombstone_relic.png"));

    public TombstoneRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);

    }

    private static final int BUFF = 4;

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {
        if(c.costType == AbstractCreatureCard.CreatureCostType.BONE)
            return BUFF;
        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        return 0;
    }


}
