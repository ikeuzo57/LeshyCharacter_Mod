package leshy.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import leshy.cards.abstracts.AbstractPeltCard;
import leshy.relics.TraderMaskRelic;

public class SellPeltEffect extends AbstractGameEffect {

    private static final float EFFECT_DUR = 1.5F;
    private static final float PADDING = 30.0F * Settings.scale;

    private AbstractPeltCard card;


    public SellPeltEffect(AbstractPeltCard pelt){

        this.card = pelt;
        this.duration = this.startingDuration = 0.1F;


    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();


        if (this.duration < 0.0F) {

            int gold = this.card.secondMagicNumber;
            for(AbstractRelic r : AbstractDungeon.player.relics)
                if(r instanceof TraderMaskRelic)
                    gold += 100;

            if (AbstractDungeon.player.hasRelic("Golden Idol"))
                gold = (int)((double)gold * 1.25);
            AbstractDungeon.player.gainGold(gold);
            CardCrawlGame.sound.play("GOLD_GAIN");

            AbstractDungeon.player.masterDeck.removeCard(this.card);

            this.isDone = true;

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
