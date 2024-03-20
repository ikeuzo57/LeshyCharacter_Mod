package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.BlackGoat;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BoonOfGoatsBloodRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(BoonOfGoatsBloodRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("boon_of_goats_blood_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("boon_of_goats_blood_relic.png"));

    public BoonOfGoatsBloodRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);

    }

    @Override
    public void atBattleStart() {
        BlackGoat goat = new BlackGoat();
        goat.baseFleeting = true;
        addToBot(new SummonCreatureAction(new CreatureOrb(goat)));
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
