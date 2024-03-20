package leshy.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.util.TextureLoader;

import java.util.ArrayList;
import java.util.function.Predicate;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class BottledOpossumRelic extends CustomRelic implements CustomBottleRelic, CustomSavable<BottledOpossumRelic.BottledOpossumSavable> {

    public static final String ID = LeshyMod.makeID(BottledOpossumRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bottled_opossum.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bottled_opossum.png"));

    private boolean cardSelected = true;

    public BottledOpossumRelic(){
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);

    }

    @Override
    public void onEquip() {

        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if(c instanceof AbstractCreatureCard)
                tmp.addToTop(c);

        if(!tmp.isEmpty()){

            this.cardSelected = false;
            if(AbstractDungeon.isScreenUp){
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }

            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(tmp, 1, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);

        }

    }

    public void onUnequip() {
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if(c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).bottledOpossum)
                ((AbstractCreatureCard) c).bottledOpossum = false;
        }
    }

    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            AbstractCreatureCard card = null;
            if(AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof AbstractCreatureCard) {
                card = (AbstractCreatureCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                card.bottledOpossum = true;
                this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(card.name, "y") + this.DESCRIPTIONS[3];
                this.tips.clear();
                this.tips.add(new PowerTip(this.name, this.description));
                initializeTips();
            }
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    public void atBattleStart() {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public Predicate<AbstractCard> isOnCard() {
        Predicate<AbstractCard> pred = c -> (c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).bottledOpossum);
        return pred;
    }

    @Override
    public BottledOpossumSavable onSave() {
        BottledOpossumSavable save = new BottledOpossumSavable();
        save.tips = this.tips;
        save.description = this.description;
        return save;
    }

    @Override
    public void onLoad(BottledOpossumSavable save) {
        this.tips = save.tips;
        this.description = save.description;
    }

    public static class BottledOpossumSavable{

        public ArrayList<PowerTip> tips = new ArrayList<>();
        public String description = "";

    }

}
