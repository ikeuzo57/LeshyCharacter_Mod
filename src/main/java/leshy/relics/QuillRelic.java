package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.actions.QuillAction;
import leshy.actions.WoodcarverAction;
import leshy.powers.BonePower;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class QuillRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = LeshyMod.makeID(QuillRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("quill_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("quill_relic.png"));

    AbstractPlayer p = AbstractDungeon.player;

    public QuillRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);

    }


    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public void onRightClick() {

        if(!AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {

            for(AbstractPower ap : p.powers){

                if(ap instanceof BonePower && ap.amount >= 4){

                    flash();
                    addToBot(new ReducePowerAction(p, p, ap, 4));
                    addToBot(new QuillAction());
                    break;

                }

            }

        }
    }
}
