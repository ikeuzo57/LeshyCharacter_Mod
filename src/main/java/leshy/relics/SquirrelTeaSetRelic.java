package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.LeshyMod;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class SquirrelTeaSetRelic extends CustomRelic {

    public static final String ID = LeshyMod.makeID(SquirrelTeaSetRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("squirrel_tea_set_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("squirrel_tea_set_relic.png"));

    private boolean firstTurn = true;

    public SquirrelTeaSetRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    public void atTurnStart() {
        if (this.firstTurn) {
            if (this.counter == -2) {
                this.pulse = false;
                this.counter = -1;
                flash();
                addToTop(new MakeTempCardInHandAction(LeshyMod.getSquirrel()));
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
            this.firstTurn = false;
        }
    }

    public void atPreBattle() {
        this.firstTurn = true;
    }

    public void onEnterRestRoom() {
        flash();
        this.counter = -2;
        this.pulse = true;
    }

    public void setCounter(int counter) {
        super.setCounter(counter);
        if (counter == -2)
            this.pulse = true;
    }

    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }

}
