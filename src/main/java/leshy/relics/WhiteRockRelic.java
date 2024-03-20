package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import leshy.LeshyMod;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class WhiteRockRelic extends CustomRelic {

    public static final String ID = LeshyMod.makeID(WhiteRockRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("white_rock_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("white_rock_relic.png"));

    public WhiteRockRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);

    }

    private static final int BUFF = 3;

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }


    public void atTurnStart(){
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, BUFF), BUFF));
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters)
            addToTop(new ApplyPowerAction(m, m, new StrengthPower(m, 1), 1));
    }


}
