package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import leshy.LeshyMod;
import leshy.cards.WolfPelt;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class InfestedPeltRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(InfestedPeltRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("infested_pelt_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("infested_pelt_relic.png"));

    public InfestedPeltRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);

    }

    @Override
    public void onEquip() {
        super.onEquip();
        for(int i=0; i<3; i++){
            float x = MathUtils.random(0.1F, 0.9F) * Settings.WIDTH;
            float y = MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT;
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new WolfPelt(), x, y));
        }
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
