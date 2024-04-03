package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.relics.interfaces.OnSacrificeRelic;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BloodstoneRelic extends CustomRelic implements CreatureValueRelic, OnSacrificeRelic {

    public static final String ID = LeshyMod.makeID(BloodstoneRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bloodstone_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bloodstone_relic.png"));


    private static final int BUFF = 1;

    public BloodstoneRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);

        this.counter = 0;


    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    public void squirrelDeath(){
        flash();
        this.counter++;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {
        if(c.tribe == AbstractCreatureCard.CreatureTribe.SQUIRREL || c.tribe == AbstractCreatureCard.CreatureTribe.AMALGAM)
            return this.counter * BUFF;
        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        if(c.tribe == AbstractCreatureCard.CreatureTribe.SQUIRREL || c.tribe == AbstractCreatureCard.CreatureTribe.AMALGAM)
            return this.counter * BUFF;
        return 0;
    }

    @Override
    public void onSacrifice(AbstractCreatureCard c, boolean diedToDamage) {
        if((c.tribe == AbstractCreatureCard.CreatureTribe.SQUIRREL || c.tribe == AbstractCreatureCard.CreatureTribe.AMALGAM) && diedToDamage)
            squirrelDeath();
    }
}
