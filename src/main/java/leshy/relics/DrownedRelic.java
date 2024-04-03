package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.relics.interfaces.CreatureSigilRelic;
import leshy.util.TextureLoader;

import java.util.HashSet;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class DrownedRelic extends CustomRelic implements CreatureSigilRelic {

    public static final String ID = LeshyMod.makeID(DrownedRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("drowned_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("drowned_relic.png"));

    public DrownedRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);

        PowerTip g = AbstractCreatureCard.getSigilPowertip(AbstractCreatureCard.Sigils.GUARDIAN);
        if(g != null)
            tips.add(g);

        PowerTip w = AbstractCreatureCard.getSigilPowertip(AbstractCreatureCard.Sigils.WATERBORNE);
        if(w != null)
            tips.add(w);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public HashSet<AbstractCreatureCard.Sigils> giveSigils(AbstractCreatureCard c) {

        HashSet<AbstractCreatureCard.Sigils> sigils = new HashSet<>();

        sigils.add(AbstractCreatureCard.Sigils.GUARDIAN);

        if(c.orb != null && AbstractDungeon.player.orbs.indexOf(c.orb) == 0)
            sigils.add(AbstractCreatureCard.Sigils.WATERBORNE);

        return sigils;

    }

}
