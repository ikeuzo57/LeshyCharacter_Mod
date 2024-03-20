package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import leshy.LeshyMod;
import leshy.powers.BonePower;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BoonOfTheBoneLordRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(BoonOfTheBoneLordRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("boon_of_the_bone_lord_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("boon_of_the_bone_lord_relic.png"));

    public BoonOfTheBoneLordRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);

        this.counter = 0;

    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new BonePower(p, null, this.counter)));
    }

    public void increase(int i){
        this.counter += i;
        updateDescription(AbstractDungeon.player.chosenClass);
    }


    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];

    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c){
        this.description = getUpdatedDescription();
        this.tips.get(0).body = this.description;
    }
}
