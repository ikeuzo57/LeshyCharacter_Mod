package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.Stoat;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class OldShellRelic extends CustomRelic implements CreatureValueRelic {

    public static final String ID = LeshyMod.makeID(OldShellRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("p03_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("p03_relic.png"));

    public OldShellRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);

    }


    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0] + AbstractCreatureCard.STOAT_BUFF + DESCRIPTIONS[1];

    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {
        if(c instanceof Stoat)
            return c.getStoatBuff();
        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        if(c instanceof Stoat)
            return c.getStoatBuff();
        return 0;
    }
}
