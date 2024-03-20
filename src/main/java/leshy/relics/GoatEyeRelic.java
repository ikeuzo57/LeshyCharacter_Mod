package leshy.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import leshy.LeshyMod;
import leshy.cards.BlackGoat;
import leshy.cards.FilmRoll;
import leshy.cards.StuntedWolf;
import leshy.characters.Leshy;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class GoatEyeRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(GoatEyeRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("goat_eye_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("goat_eye_relic.png"));

    public GoatEyeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip) {
        super.instantObtain(p, slot, callOnEquip);
        this.onEquip();
    }

    @Override
    public void onEquip() {
        if(AbstractDungeon.player instanceof Leshy)
            ((Leshy) AbstractDungeon.player).isSexy = true;
    }

    @Override
    public void onUnequip() {
        if(AbstractDungeon.player instanceof Leshy)
            ((Leshy) AbstractDungeon.player).isSexy = false;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }


}
