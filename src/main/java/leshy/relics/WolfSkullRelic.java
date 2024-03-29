package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.DireWolf;
import leshy.cards.DireWolfPup;
import leshy.cards.Wolf;
import leshy.cards.WolfCub;
import leshy.relics.interfaces.CreatureSigilRelic;
import leshy.util.TextureLoader;

import java.util.HashSet;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class WolfSkullRelic extends CustomRelic implements CreatureSigilRelic {

    public static final String ID = LeshyMod.makeID(WolfSkullRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("wolf_skull_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("wolf_skull_relic.png"));

    public WolfSkullRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);

        tips.add(new PowerTip("Leader", "Other creatures with the same tribe as this one gain 4 Attack."));

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public HashSet<AbstractCreatureCard.Sigils> giveSigils(AbstractCreatureCard c) {

        HashSet<AbstractCreatureCard.Sigils> sigils = new HashSet<>();

        if(c instanceof Wolf || c instanceof WolfCub || c instanceof DireWolfPup || c instanceof DireWolf)
            sigils.add(AbstractCreatureCard.Sigils.LEADER);

        return sigils;

    }

}
