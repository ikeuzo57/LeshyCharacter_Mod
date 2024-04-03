package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.RiverSnapper;
import leshy.relics.interfaces.CreatureSigilRelic;
import leshy.util.TextureLoader;

import java.util.HashSet;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class GreenShellRelic extends CustomRelic implements CreatureSigilRelic {

    public static final String ID = LeshyMod.makeID(GreenShellRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("green_shell_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("green_shell_relic.png"));

    public GreenShellRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);

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

        if(c instanceof RiverSnapper)
            sigils.add(AbstractCreatureCard.Sigils.ARMORED);

        return sigils;

    }

}
