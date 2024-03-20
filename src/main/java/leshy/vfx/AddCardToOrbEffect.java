package leshy.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.orbs.CreatureOrb;

public class AddCardToOrbEffect extends AbstractGameEffect {

    private static final float EFFECT_DUR = 1.5F;
    private static final float PADDING = 30.0F * Settings.scale;

    private AbstractCreatureCard card;

    private CreatureOrb o;
    private float glowPoint;
    private boolean glowStartHit;

    public AddCardToOrbEffect(AbstractCreatureCard srcCard, CreatureOrb o, float startX, float startY, boolean instant){

        this.card = srcCard;
        this.duration = this.startingDuration = instant ? 0.1F : 1.0F;
        this.glowPoint = this.startingDuration * 0.25F;
        this.card.target_x = startX;
        this.card.target_y = startY;
        this.card.targetDrawScale = 0.75F;
        this.o = o;

    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();

        if (this.duration < this.glowPoint) {
            if (!this.glowStartHit) {
                this.card.beginGlowing();
                this.card.superFlash(Color.GOLDENROD);
                this.glowStartHit = true;
            }
            if (this.duration < 0.0F) {
                this.isDone = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }
    }

    @Override
    public void dispose() {
    }
}
