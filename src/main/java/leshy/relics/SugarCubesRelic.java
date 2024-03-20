package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.relics.interfaces.OnSummonRelic;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class SugarCubesRelic extends CustomRelic implements OnSummonRelic {

    public static final String ID = LeshyMod.makeID(SugarCubesRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("sugar_cubes_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("sugar_cubes_relic.png"));


    public boolean firstCreature = false;

    public SugarCubesRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);

    }

    @Override
    public void atTurnStart() {
        firstCreature = true;
        grayscale = false;
    }

    @Override
    public void onSummon(AbstractCreatureCard c) {
        if(firstCreature && (!c.fleeting || c.current.contains(AbstractCreatureCard.Sigils.UNKILLABLE))) {
            firstCreature = false;
            grayscale = true;
            flash();
            c.upgrade();
        }
    }


    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
