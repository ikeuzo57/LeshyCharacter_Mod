package leshy.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import leshy.LeshyMod;
import leshy.cards.PackRat;

import static leshy.LeshyMod.makeEventPath;

public class PackEvent extends AbstractImageEvent {


    public static final String ID = LeshyMod.makeID("PackEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("PackEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public boolean packRat;

    public PackEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        packRat = true;
        for(AbstractPotion ap : AbstractDungeon.player.potions){
            if(ap instanceof PotionSlot){
                LeshyMod.logger.info("No Pack Rat");
                packRat = false;
                break;
            }
        }

        this.imageEventText.setDialogOption(OPTIONS[0]);

        this.hasDialog = true;
        this.hasFocus = true;
        this.combatTime = false;

        this.noCardsInRewards = true;

    }

    @Override
    public void onEnterRoom() {
        GenericEventDialog.show();
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {

            //Intro screen

            case 0:

                if(packRat){
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                    this.imageEventText.clearRemainingOptions();
                }else{
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                    this.imageEventText.clearRemainingOptions();
                }

                screenNum = 1;
                break;

            case 1:

                if(packRat){
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new PackRat(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                    this.imageEventText.clearRemainingOptions();
                }else{
                    GenericEventDialog.hide();
                    (AbstractDungeon.getCurrRoom()).rewards.clear();
                    (AbstractDungeon.getCurrRoom()).rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                    (AbstractDungeon.getCurrRoom()).rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                    if (AbstractDungeon.ascensionLevel < 15)
                        (AbstractDungeon.getCurrRoom()).rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                    (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
                    AbstractDungeon.combatRewardScreen.open();
                }

                screenNum = 2;
                break;

            case 2:
                openMap();
                break;

        }
    }


    @Override
    public void render(SpriteBatch sb) {
        this.imageEventText.render(sb);
    }


}
