package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.BaitBucket;
import leshy.cards.Boulder;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BoulderRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(BoulderRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("boulder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("boulder_relic.png"));

    public static int BOULDER = 8;

    public BoulderRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);

    }

    @Override
    public void onPlayerEndTurn() {

        int remaining = 0;
        for(AbstractOrb o : AbstractDungeon.player.orbs){
            if(o instanceof CreatureOrb){
                remaining += (o.evokeAmount);
            }
        }

        if(remaining < BOULDER){
            flash();
            Boulder boulder = new Boulder();
            boulder.health = boulder.baseHealth = BOULDER-remaining;
            addToBot(new SummonCreatureAction(new CreatureOrb(boulder)));
        }

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0] + BOULDER + DESCRIPTIONS[1];

    }
}
