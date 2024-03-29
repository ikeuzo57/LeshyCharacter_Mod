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

public class HeraldOfTheScurryRelic extends CustomRelic implements CreatureSigilRelic {

    public static final String ID = LeshyMod.makeID(HeraldOfTheScurryRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("herald_of_the_scurry_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("herald_of_the_scurry_relic.png"));

    public HeraldOfTheScurryRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);

        PowerTip remove = null;
        for(PowerTip tip : tips){
            if(tip.header.equals("Strike")){
                remove = tip;
                break;
            }
        }
        if(remove != null)
            tips.remove(remove);

        tips.add(new PowerTip("Double Strike", "Attacks the enemy an additional time. Attacks a different enemy if Bifurcated."));
        tips.add(new PowerTip("Leader", "Other creatures with the same tribe as this one gain 4 Attack."));
        tips.add(new PowerTip("Rampager", "At the end of turn, this moves in a direction."));

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public HashSet<AbstractCreatureCard.Sigils> giveSigils(AbstractCreatureCard c) {

        HashSet<AbstractCreatureCard.Sigils> sigils = new HashSet<>();

        if(c.tribe == AbstractCreatureCard.CreatureTribe.SQUIRREL || c.tribe == AbstractCreatureCard.CreatureTribe.AMALGAM){
            sigils.add(AbstractCreatureCard.Sigils.DOUBLE_STRIKE);
            sigils.add(AbstractCreatureCard.Sigils.LEADER);
            sigils.add(AbstractCreatureCard.Sigils.RAMPAGER);
        }

        return sigils;

    }

}
