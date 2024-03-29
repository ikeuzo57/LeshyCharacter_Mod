package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.Boulder;
import leshy.cards.Starvation;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class StarvationRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(StarvationRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("starvation_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("starvation_relic.png"));

    public StarvationRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.SOLID);

        tips.add(new PowerTip("Starvation", "0/1 with #yWaterborne and cannot be sacrificed."));
        tips.add(new PowerTip("Waterborne", "No longer prevents damage."));

    }

    @Override
    public void atTurnStart() {

        boolean summon = true;
        for(AbstractOrb o : AbstractDungeon.player.orbs)
            if(o instanceof CreatureOrb && ((CreatureOrb) o).creatureCard instanceof Starvation){
                summon = false;
                break;
            }

        if(summon){
            flash();
            addToBot(new SummonCreatureAction(new CreatureOrb(new Starvation())));
        }

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
