package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import leshy.LeshyMod;
import leshy.actions.SummonCreatureAction;
import leshy.cards.BaitBucket;
import leshy.cards.FilmRoll;
import leshy.cards.StuntedWolf;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class MagicEyeRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(MagicEyeRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("magic_eye_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("magic_eye_relic.png"));

    public MagicEyeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);

    }

    @Override
    public void onEquip() {
        StuntedWolf wolf = new StuntedWolf();
        FilmRoll film = new FilmRoll();
        AbstractDungeon.player.masterDeck.addToTop(wolf);
        AbstractDungeon.player.masterDeck.addToTop(film);
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            addToBot(new MakeTempCardInHandAction(wolf.makeSameInstanceOf()));
            addToBot(new MakeTempCardInHandAction(film.makeSameInstanceOf()));
        }
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
