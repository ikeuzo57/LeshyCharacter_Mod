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
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractPeltCard;
import leshy.cards.GoldenPelt;
import leshy.cards.RabbitPelt;
import leshy.cards.WolfPelt;

public class ReplacePeltEffect extends AbstractGameEffect {

    private boolean openedScreen = false;

    private Color screenColor = AbstractDungeon.fadeColor.cpy();

    private int count;

    public ReplacePeltEffect(int count) {
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        this.count = count;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            int displayCount = 0;
            for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                if(c instanceof AbstractCreatureCard){
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    c.untip();
                    c.unhover();
                    c.isGlowing = false;
                    float x = Settings.WIDTH / 3.0F + ((displayCount+AbstractDungeon.gridSelectScreen.selectedCards.size()) * Settings.WIDTH / 6.0F);
                    switch (c.rarity) {
                        case RARE:
                            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new GoldenPelt(), x, Settings.HEIGHT / 2.0F));
                            break;
                        case UNCOMMON:
                            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new WolfPelt(), x, Settings.HEIGHT / 2.0F));
                            break;
                        default:
                            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new RabbitPelt(), x, Settings.HEIGHT / 2.0F));
                            break;
                    }
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, Settings.WIDTH / 3.0F + (displayCount * Settings.WIDTH / 6.0F), Settings.HEIGHT / 2.0F));
                    displayCount++;
                }
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        if (this.duration < 1.0F && !this.openedScreen) {
            this.openedScreen = true;
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
                if(c instanceof AbstractCreatureCard && !(c instanceof AbstractPeltCard))
                    tmp.addToTop(c);
            AbstractDungeon.gridSelectScreen.open(tmp, count, true, "Choose up to " + count + " creatures to replace with Pelts.");
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
