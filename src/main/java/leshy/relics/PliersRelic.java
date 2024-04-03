package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import leshy.LeshyMod;
import leshy.cards.David;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.potions.ToothPotion;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class PliersRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = LeshyMod.makeID(PliersRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("pliers_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("pliers_relic.png"));

    public static final int HP_LOSS = 10;

    public PliersRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);

        ToothPotion tooth = new ToothPotion();
        tips.add(new PowerTip(tooth.name, tooth.description));

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0] + HP_LOSS + DESCRIPTIONS[1];

    }

    @Override
    public void onRightClick() {

        AbstractDungeon.player.damage(new DamageInfo(null, HP_LOSS, DamageInfo.DamageType.HP_LOSS));
        AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(new ToothPotion()));

    }

}


