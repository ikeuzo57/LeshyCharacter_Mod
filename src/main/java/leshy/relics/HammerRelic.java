package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.actions.HammerAction;
import leshy.actions.WoodcarverAction;
import leshy.cards.Starvation;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class HammerRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = LeshyMod.makeID(HammerRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("hammer_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("hammer_relic.png"));

    boolean used = false;

    public HammerRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        used = false;
        grayscale = false;
        flash();
    }

    @Override
    public void onRightClick() {

        if(!AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !used){

            for(AbstractOrb o : AbstractDungeon.player.orbs){

                if(o instanceof CreatureOrb && !(((CreatureOrb) o).creatureCard instanceof Starvation)){

                    used = true;
                    grayscale = true;

                    this.flash();
                    addToBot(new HammerAction());

                    break;
                }

            }

        }
    }

}


