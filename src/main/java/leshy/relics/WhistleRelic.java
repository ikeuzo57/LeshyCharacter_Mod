package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.TargetAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.relics.interfaces.ClickMonsterRelic;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class WhistleRelic extends CustomRelic implements ClickMonsterRelic {

    public static final String ID = LeshyMod.makeID(WhistleRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("whistle_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("whistle_relic.png"));



    public WhistleRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);

        PowerTip t = AbstractCreatureCard.getPowerTip(AbstractCreatureCard.MISC_DESCRIPTION[14]);
        if(t != null)
            tips.add(t);

    }


    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public void onClick(AbstractMonster m) {
        flash();
        addToBot(new TargetAction(m));
        if (AbstractDungeon.player.hasPower("Surrounded")){
            AbstractDungeon.player.flipHorizontal = (m.drawX < AbstractDungeon.player.drawX);
            m.removeSurroundedPower();
        }

    }
}
