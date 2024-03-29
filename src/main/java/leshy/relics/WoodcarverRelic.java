package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.actions.WoodcarverAction;
import leshy.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class WoodcarverRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = LeshyMod.makeID(WoodcarverRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("woodcarver_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("woodcarver_relic.png"));

    boolean used = false;

    public WoodcarverRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        used = false;
        grayscale = false;
        flash();
    }

    @Override
    public void onRightClick() {

        if(!AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !used){

            used = true;
            grayscale = true;

            this.flash();
            addToBot(new WoodcarverAction());

        }
    }

}


