package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class ExoticPetFoodRelic extends CustomRelic implements CreatureValueRelic {

    public static final String ID = LeshyMod.makeID(ExoticPetFoodRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("exotic_pet_food_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("exotic_pet_food_relic.png"));

    public ExoticPetFoodRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);

    }

    private static final int BUFF = 1;

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {
        if(c.tribe == AbstractCreatureCard.CreatureTribe.NONE)
            return BUFF;
        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        if(c.tribe == AbstractCreatureCard.CreatureTribe.NONE)
            return BUFF;
        return 0;
    }
}
