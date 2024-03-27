package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.actions.BuffAllCreaturesAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class TinyHeadbandRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(TinyHeadbandRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tiny_headband_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tiny_headband_relic.png"));

    public TinyHeadbandRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);

        this.counter = 0;

    }


    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if(c instanceof AbstractCreatureCard && (((AbstractCreatureCard) c).tribe == AbstractCreatureCard.CreatureTribe.SQUIRREL || ((AbstractCreatureCard) c).tribe == AbstractCreatureCard.CreatureTribe.AMALGAM)){
            this.counter++;
            if(this.counter >= 3)
                beginLongPulse();
        }
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        if(this.counter >= 3){
            flash();
            addToBot(new BuffAllCreaturesAction(2));
        }
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        stopPulse();
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

}
