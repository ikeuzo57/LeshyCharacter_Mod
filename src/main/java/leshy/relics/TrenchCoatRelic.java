package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class TrenchCoatRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(TrenchCoatRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("trench_coat_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("trench_coat_relic.png"));

    private boolean cardSelected = true;

    public TrenchCoatRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);

    }

    @Override
    public void onEquip() {
        this.cardSelected = false;

        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;

        }
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;

        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if(c instanceof AbstractCreatureCard && !((AbstractCreatureCard) c).bloodless){
                temp.addToTop(c);
            }
        }
        if(temp.size() > 0){
            AbstractDungeon.gridSelectScreen.open(temp, 1, this.DESCRIPTIONS[1], false, false, false, false);
        }

    }

    @Override
    public void update() {
        super.update();

        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            this.cardSelected = true;
            AbstractCard c = (AbstractDungeon.gridSelectScreen.selectedCards.get(0));

            if(c instanceof AbstractCreatureCard && AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof AbstractCreatureCard){
                ((AbstractCreatureCard) c).tribe = AbstractCreatureCard.CreatureTribe.SQUIRREL;
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
        }

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
