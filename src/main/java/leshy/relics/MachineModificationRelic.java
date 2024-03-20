package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.LeshyMod;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class MachineModificationRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(MachineModificationRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("machine_modification_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("machine_modification_relic.png"));

    public MachineModificationRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);

    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.masterMaxOrbs++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.masterMaxOrbs--;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
