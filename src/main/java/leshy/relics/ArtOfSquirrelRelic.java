package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class ArtOfSquirrelRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(ArtOfSquirrelRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("art_of_squirrel_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("art_of_squirrel_relic.png"));

    private boolean active = false;
    private boolean firstTurn = false;


    public ArtOfSquirrelRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);

    }

    @Override
    public void atPreBattle(){
        flash();
        this.firstTurn = true;
        this.active =  true;
        if (!this.pulse) {
            beginPulse();
            this.pulse = true;
        }
    }

    @Override
    public void atTurnStart() {
        beginPulse();
        this.pulse = true;
        if(this.active && !this.firstTurn){
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new MakeTempCardInHandAction(LeshyMod.getSquirrel()));
        }
        this.firstTurn = false;
        this.active =  true;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if(c instanceof AbstractCreatureCard){
            this.active = false;
            this.pulse = false;
        }
    }

    public void onVictory() {
        this.pulse = false;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

}
