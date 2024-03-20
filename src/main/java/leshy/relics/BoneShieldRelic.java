package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.brashmonkey.spriter.Timeline;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.actions.WoodcarverAction;
import leshy.powers.BonePower;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BoneShieldRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = LeshyMod.makeID(BoneShieldRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bone_shield_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bone_shield_relic.png"));

    public static final int COST = 4;
    public static final int BLOCK = 2;

    public BoneShieldRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public void onRightClick() {

        if(!AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){

            for(AbstractPower ap : AbstractDungeon.player.powers){
                if(ap instanceof BonePower && ap.amount >= COST){
                    flash();
                    addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, ap, COST));
                    addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK));
                    break;
                }
            }

        }
    }

}


