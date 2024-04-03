package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.Mantis;
import leshy.cards.MantisGod;
import leshy.relics.interfaces.CreatureSigilRelic;
import leshy.util.TextureLoader;

import java.util.HashSet;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class MantisClawRelic extends CustomRelic implements CreatureSigilRelic {

    public static final String ID = LeshyMod.makeID(MantisClawRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("mantis_claw_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("mantis_claw_relic.png"));

    public MantisClawRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);

        PowerTip remove = null;
        for(PowerTip tip : tips){
            if(tip.header.equals("Strike")){
                remove = tip;
                break;
            }
        }
        if(remove != null)
            tips.remove(remove);

        PowerTip ds = AbstractCreatureCard.getSigilPowertip(AbstractCreatureCard.Sigils.DOUBLE_STRIKE);
        if(ds != null)
            tips.add(ds);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public HashSet<AbstractCreatureCard.Sigils> giveSigils(AbstractCreatureCard c) {

        HashSet<AbstractCreatureCard.Sigils> sigils = new HashSet<>();

        if(c instanceof Mantis || c instanceof MantisGod)
            sigils.add(AbstractCreatureCard.Sigils.DOUBLE_STRIKE);

        return sigils;

    }

}
