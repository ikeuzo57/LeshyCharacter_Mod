package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.relics.interfaces.CreatureSigilRelic;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.util.TextureLoader;

import java.util.HashSet;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class SnorkelRelic extends CustomRelic implements CreatureSigilRelic, CreatureValueRelic {

    public static final String ID = LeshyMod.makeID(SnorkelRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("snorkel_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("snorkel_relic.png"));

    public static final int BUFF = 6;

    public SnorkelRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);

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

        if(c.tribe == AbstractCreatureCard.CreatureTribe.SQUIRREL || c.tribe == AbstractCreatureCard.CreatureTribe.AMALGAM)
            sigils.add(AbstractCreatureCard.Sigils.WATERBORNE);

        return sigils;

    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {
        if(c.tribe == AbstractCreatureCard.CreatureTribe.SQUIRREL || c.tribe == AbstractCreatureCard.CreatureTribe.AMALGAM)
            return BUFF;
        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        return 0;
    }
}
