package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.actions.CreatureSacrificeAction;
import leshy.actions.SummonCreatureAction;
import leshy.actions.TheMoonAction;
import leshy.cards.BaitBucket;
import leshy.cards.TheMoon;
import leshy.orbs.CreatureOrb;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class TheMoonRelic extends CustomRelic implements OnPlayerDeathRelic {

    public static final String ID = LeshyMod.makeID(TheMoonRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("the_moon_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("the_moon_relic.png"));

    public TheMoonRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);

    }



    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {

        if(this.counter != -2){
            this.counter = -2;
            usedUp();

            abstractPlayer.heal(AbstractDungeon.player.maxHealth/10);
            if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
                addToBot(new TheMoonAction());

            return false;
        }

        return true;
    }

    @Override
    public void setCounter(int counter) {
        if (counter == -2) {
            usedUp();
            this.counter = -2;
        }
    }
}
