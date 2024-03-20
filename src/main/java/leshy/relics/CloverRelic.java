package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import leshy.LeshyMod;
import leshy.actions.WoodcarverAction;
import leshy.util.TextureLoader;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnRandomRelicTier;
import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class CloverRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = LeshyMod.makeID(CloverRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("clover_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("clover_relic.png"));

    public CloverRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);

        this.counter = 3;

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter <= 0)
            usedUp();
    }

    @Override
    public void onRightClick() {

        if(this.counter > 0){

            if(AbstractDungeon.isScreenUp && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD && !AbstractDungeon.combatRewardScreen.rewards.isEmpty()){

                this.counter--;
                this.flash();
                if (this.counter <= 0)
                    usedUp();

                for(RewardItem ri : AbstractDungeon.combatRewardScreen.rewards){

                    switch (ri.type){
                        case CARD:
                            ri.cards = AbstractDungeon.getRewardCards();
                            break;
                        case RELIC:
                            ri.relic = AbstractDungeon.returnRandomNonCampfireRelic(ri.relic.tier);
                            ri.text = ri.relic.name;
                            break;
                        case POTION:
                            ri.potion = AbstractDungeon.returnRandomPotion();
                            break;
                    }

                }

            }else if(AbstractDungeon.isScreenUp && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD && !AbstractDungeon.bossRelicScreen.relics.isEmpty()){

                this.counter--;
                this.flash();
                if (this.counter <= 0)
                    usedUp();

                ArrayList<AbstractRelic> chosenRelics = new ArrayList<>();
                for (int i = 0; i < 3; i++)
                    chosenRelics.add(AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));

                AbstractDungeon.bossRelicScreen.relics.clear();

                float SLOT_1_X = Settings.WIDTH / 2.0F + 4.0F * Settings.scale, SLOT_1_Y = AbstractDungeon.floorY + 360.0F * Settings.scale;
                float SLOT_2_X = Settings.WIDTH / 2.0F - 116.0F * Settings.scale, SLOT_2_Y = AbstractDungeon.floorY + 225.0F * Settings.scale;
                float SLOT_3_X = Settings.WIDTH / 2.0F + 124.0F * Settings.scale;

                AbstractRelic r = chosenRelics.get(0);
                r.spawn(SLOT_1_X, SLOT_1_Y);
                r.hb.move(r.currentX, r.currentY);
                AbstractDungeon.bossRelicScreen.relics.add(r);
                AbstractRelic r2 = chosenRelics.get(1);
                r2.spawn(SLOT_2_X, SLOT_2_Y);
                r2.hb.move(r2.currentX, r2.currentY);
                AbstractDungeon.bossRelicScreen.relics.add(r2);
                AbstractRelic r3 = chosenRelics.get(2);
                r3.spawn(SLOT_3_X, SLOT_2_Y);
                r3.hb.move(r3.currentX, r3.currentY);
                AbstractDungeon.bossRelicScreen.relics.add(r3);

                for (AbstractRelic r1 : AbstractDungeon.bossRelicScreen.relics)
                    UnlockTracker.markRelicAsSeen(r1.relicId);

            }

        }


    }

}


