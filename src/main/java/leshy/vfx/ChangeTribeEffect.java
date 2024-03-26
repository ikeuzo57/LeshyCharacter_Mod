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

public class ChangeTribeEffect extends AbstractGameEffect {

    private boolean openedScreen = false;

    private Color screenColor = AbstractDungeon.fadeColor.cpy();

    private AbstractCreatureCard.CreatureTribe tribe;

    public ChangeTribeEffect(AbstractCreatureCard.CreatureTribe tribe) {
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        this.tribe = tribe;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                if(c instanceof AbstractCreatureCard){
                    ((AbstractCreatureCard) c).tribe = this.tribe;
                    ((AbstractCreatureCard) c).initializeDescription();
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        if (this.duration < 1.0F && !this.openedScreen) {
            this.openedScreen = true;
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
                if(c instanceof AbstractCreatureCard)
                    tmp.addToTop(c);
            AbstractDungeon.gridSelectScreen.open(tmp, 1, "Pick a creature to become a " + AbstractCreatureCard.tribeText(this.tribe), false, false, false, false);
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
