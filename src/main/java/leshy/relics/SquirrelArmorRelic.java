package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.relics.interfaces.CreatureSigilRelic;
import leshy.util.TextureLoader;

import java.util.HashSet;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class SquirrelArmorRelic extends CustomRelic implements CreatureSigilRelic {

    public static final String ID = LeshyMod.makeID(SquirrelArmorRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("squirrel_armor_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("squirrel_armor_relic.png"));

    public SquirrelArmorRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);

        PowerTip a = AbstractCreatureCard.getSigilPowertip(AbstractCreatureCard.Sigils.ARMORED);
        if(a != null)
            tips.add(a);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public HashSet<AbstractCreatureCard.Sigils> giveSigils(AbstractCreatureCard c) {

        HashSet<AbstractCreatureCard.Sigils> sigils = new HashSet<>();

        if(c.tribe == AbstractCreatureCard.CreatureTribe.SQUIRREL || c.tribe == AbstractCreatureCard.CreatureTribe.AMALGAM)
            sigils.add(AbstractCreatureCard.Sigils.ARMORED);

        return sigils;

    }

}
