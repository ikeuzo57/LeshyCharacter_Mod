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

import java.util.HashSet;

public class SigilSwapEffect extends AbstractGameEffect {

    private boolean openedScreen = false;

    private Color screenColor = AbstractDungeon.fadeColor.cpy();

    public SigilSwapEffect() {
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        if (!AbstractDungeon.isScreenUp && AbstractDungeon.gridSelectScreen.selectedCards.size() == 2) {
            float displayCount = 0.0F;

            if(AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof AbstractCreatureCard && AbstractDungeon.gridSelectScreen.selectedCards.get(1) instanceof AbstractCreatureCard){

                AbstractCreatureCard c1 = (AbstractCreatureCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractCreatureCard c2 = (AbstractCreatureCard) AbstractDungeon.gridSelectScreen.selectedCards.get(1);

                HashSet<AbstractCreatureCard.Sigils> tempGained = c1.gained;
                c1.gained = c2.gained;
                c2.gained = tempGained;

                c1.gained.removeAll(c1.innate);
                c2.gained.removeAll(c2.innate);

                c1.current.clear();
                c1.current.addAll(c1.innate);
                c1.current.addAll(c1.gained);

                c2.current.clear();
                c2.current.addAll(c2.innate);
                c2.current.addAll(c2.gained);

                c1.initializeDescription();
                c2.initializeDescription();

                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c1.makeStatEquivalentCopy(), Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F));
                displayCount += Settings.WIDTH / 6.0F;
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c2.makeStatEquivalentCopy(), Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        if (this.duration < 1.0F && !this.openedScreen) {
            this.openedScreen = true;
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
                if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).bloodless)
                    tmp.addToTop(c);
            AbstractDungeon.gridSelectScreen.open(tmp, 2, "Choose 2 creatures.", false, false, false, false);
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
