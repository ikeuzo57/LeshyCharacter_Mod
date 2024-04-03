package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.powers.BonePower;
import leshy.relics.interfaces.CreatureSigilRelic;
import leshy.util.TextureLoader;

import java.util.HashSet;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class InfestationRelic extends CustomRelic implements CreatureSigilRelic {

    public static final String ID = LeshyMod.makeID(InfestationRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("infestation_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("infestation_relic.png"));

    public InfestationRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);

        PowerTip as = AbstractCreatureCard.getSigilPowertip(AbstractCreatureCard.Sigils.ANT_SPAWNER);
        if(as != null)
            tips.add(as);

        PowerTip u = AbstractCreatureCard.getSigilPowertip(AbstractCreatureCard.Sigils.UNKILLABLE);
        if(u != null)
            tips.add(u);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public HashSet<AbstractCreatureCard.Sigils> giveSigils(AbstractCreatureCard c) {

        HashSet<AbstractCreatureCard.Sigils> sigils = new HashSet<>();

        if(c.tribe != AbstractCreatureCard.CreatureTribe.SQUIRREL && c.tribe != AbstractCreatureCard.CreatureTribe.AMALGAM)
            sigils.add(AbstractCreatureCard.Sigils.ANT_SPAWNER);

        if(c.tribe == AbstractCreatureCard.CreatureTribe.ANT || c.tribe == AbstractCreatureCard.CreatureTribe.AMALGAM)
            sigils.add(AbstractCreatureCard.Sigils.UNKILLABLE);

        return sigils;

    }


}
