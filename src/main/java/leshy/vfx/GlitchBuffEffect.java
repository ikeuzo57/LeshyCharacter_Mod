package leshy.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import leshy.cards.abstracts.AbstractCreatureCard;

public class GlitchBuffEffect extends AbstractGameEffect {

    private boolean openedScreen = false;

    private static int UPGRADE_COUNT = 3;

    private Color screenColor = AbstractDungeon.fadeColor.cpy();
    private int count;

    public GlitchBuffEffect(int count) {
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();

        this.count = count;
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            float displayCount = 0.0F;
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                if(c instanceof AbstractCreatureCard){
                    ((AbstractCreatureCard) c).isStatic = true;
                    for(int i=0; i<UPGRADE_COUNT; i++)
                        c.upgrade();
                    AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F));
                    displayCount += Settings.WIDTH / 6.0F;
                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        if (this.duration < 1.0F && !this.openedScreen) {
            this.openedScreen = true;
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
                if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).isStatic)
                    tmp.addToTop(c);
            AbstractDungeon.gridSelectScreen.open(tmp, this.count, true, "Choose up to " + this.count + " creatures.");
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID)
            AbstractDungeon.gridSelectScreen.render(sb);
    }

    public void dispose() {}

}
