package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.LeshyMod;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class PileOfTeethRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(PileOfTeethRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("pile_of_teeth_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("pile_of_teeth_relic.png"));

    public PileOfTeethRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);

    }


    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {

        int overkill = damageAmount - target.currentHealth;
        if(overkill > 0){

            if (AbstractDungeon.player.hasRelic("Golden Idol"))
                overkill = (int)((double)overkill * 1.25);
            AbstractDungeon.player.gainGold(overkill);
            CardCrawlGame.sound.play("GOLD_GAIN");

        }

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

}
