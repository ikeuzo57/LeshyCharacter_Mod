package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractPeltCard;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class TrapperMaskRelic extends CustomRelic implements CreatureValueRelic {

    public static final String ID = LeshyMod.makeID(TrapperMaskRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("trapper_mask_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("trapper_mask_relic.png"));

    public static final int BUFF = 10;

    public TrapperMaskRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.SOLID);

    }

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
        if(c instanceof AbstractPeltCard)
            return BUFF;
        return 0;
    }
}
