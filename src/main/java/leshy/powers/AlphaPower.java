package leshy.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.LeshyMod;
import leshy.cards.*;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.powers.interfaces.CreatureValuePower;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class AlphaPower extends AbstractPower implements CloneablePowerInterface, InvisiblePower, CreatureValuePower {
    public AbstractCreature source;

    public static final String POWER_ID = LeshyMod.makeID(AlphaPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("bone_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("bone_power32.png"));

    private AbstractCreatureCard alpha;

    private static final int BUFF = 4;
    private AbstractCreatureCard.CreatureTribe tribe;

    private static int baitIdOffset = 0;

    public AlphaPower(AbstractCreatureCard alpha) {
        name = NAME;
        ID = POWER_ID + baitIdOffset;
        baitIdOffset++;

        this.owner = AbstractDungeon.player;
        this.source = AbstractDungeon.player;

        this.alpha = alpha;
        this.tribe = alpha.tribe;
        if(this.tribe == AbstractCreatureCard.CreatureTribe.ANT)
            this.tribe = AbstractCreatureCard.CreatureTribe.INSECT;
        if(this.tribe == AbstractCreatureCard.CreatureTribe.NONE && LeshyMod.cawCaw)
            this.tribe = AbstractCreatureCard.CreatureTribe.AVIAN;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }



    @Override
    public AbstractPower makeCopy() {
        return new AlphaPower(alpha);
    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {

        if(c.uuid == this.alpha.uuid)
            return 0;

        AbstractCreatureCard.CreatureTribe theirTribe = c.tribe;

        if(theirTribe == AbstractCreatureCard.CreatureTribe.NONE && LeshyMod.cawCaw)
            theirTribe = AbstractCreatureCard.CreatureTribe.AVIAN;

        if(theirTribe == AbstractCreatureCard.CreatureTribe.ANT)
            theirTribe = AbstractCreatureCard.CreatureTribe.INSECT;

        if(this.tribe == AbstractCreatureCard.CreatureTribe.NONE || theirTribe == AbstractCreatureCard.CreatureTribe.NONE)
            return 0;

        if(this.tribe == AbstractCreatureCard.CreatureTribe.AMALGAM || theirTribe == AbstractCreatureCard.CreatureTribe.AMALGAM || this.tribe == theirTribe){
            if(LeshyMod.wolfSkull && (this.alpha instanceof Wolf || this.alpha instanceof WolfCub || this.alpha instanceof DireWolfPup || this.alpha instanceof DireWolf))
                return BUFF*2;
            return BUFF;
        }

        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        return 0;
    }
}
