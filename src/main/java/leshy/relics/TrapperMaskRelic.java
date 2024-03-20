package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.GoldenPelt;
import leshy.orbs.CreatureOrb;
import leshy.powers.TrapperPower;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class TrapperMaskRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(TrapperMaskRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("trapper_mask_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("trapper_mask_relic.png"));

    public TrapperMaskRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.SOLID);

    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new TrapperPower(p, p, 10)));
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
