package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import leshy.LeshyMod;
import leshy.powers.BonePower;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BoneLordsHornRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = LeshyMod.makeID(BoneLordsHornRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bone_lords_horn_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bone_lords_horn_relic.png"));

    boolean usedThisTurn = false;

    public BoneLordsHornRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public void atTurnStart() {
        usedThisTurn = false;
        grayscale = false;
    }

    @Override
    public void onRightClick() {

        if(!AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !usedThisTurn){

            AbstractPlayer p = AbstractDungeon.player;

            int effect = EnergyPanel.totalCount;
            if (p.hasRelic("Chemical X")) {
                effect += 2;
                p.getRelic("Chemical X").flash();
            }

            if(effect > 0){

                flash();
                usedThisTurn = true;
                grayscale = true;
                addToBot(new ApplyPowerAction(p, p, new BonePower(p, null, effect)));
                p.energy.use(EnergyPanel.totalCount);

            }

        }
    }

}


