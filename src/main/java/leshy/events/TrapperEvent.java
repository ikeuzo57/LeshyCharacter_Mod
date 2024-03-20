package leshy.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import leshy.LeshyMod;
import leshy.cards.*;

import static leshy.LeshyMod.makeEventPath;

public class TrapperEvent extends AbstractImageEvent {


    public static final String ID = LeshyMod.makeID("TrapperEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("TrapperEvent.png");

    //private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private int rabbit = 25;
    private int wolf = 45;
    private int golden = 120;
    private int knife = 150;


    public TrapperEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        if (AbstractDungeon.ascensionLevel >= 15){
            rabbit += 5;
            wolf += 5;
            golden += 5;
            knife += 25;
        }

        updateButtons();

        this.hasDialog = true;
        this.hasFocus = true;
        this.combatTime = false;

    }

    @Override
    public void onEnterRoom() {
        GenericEventDialog.show();
    }

    @Override
    protected void buttonEffect(int i) {

        switch (i) {
            case 0:
                AbstractDungeon.player.loseGold(rabbit);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new RabbitPelt(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                updateButtons();
                break;

            case 1:
                AbstractDungeon.player.loseGold(wolf);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new WolfPelt(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                updateButtons();
                break;

            case 2:
                AbstractDungeon.player.loseGold(golden);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new GoldenPelt(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                updateButtons();
                break;

            case 3:
                AbstractDungeon.player.loseGold(knife);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Knife(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                updateButtons();
                break;

            case 4:
                openMap();
                break;

        }


    }


    public void updateButtons(){

        this.imageEventText.clearAllDialogs();

        int gold = AbstractDungeon.player.gold;

        if(gold >= rabbit)
            this.imageEventText.setDialogOption(OPTIONS[0] + rabbit + OPTIONS[1], new RabbitPelt());
        else
            this.imageEventText.setDialogOption(OPTIONS[0] + rabbit + OPTIONS[1], true, new RabbitPelt());

        if(gold >= wolf)
            this.imageEventText.setDialogOption(OPTIONS[2] + wolf + OPTIONS[3], new WolfPelt());
        else
            this.imageEventText.setDialogOption(OPTIONS[2] + wolf + OPTIONS[3], true, new WolfPelt());

        if(gold >= golden)
            this.imageEventText.setDialogOption(OPTIONS[4] + golden + OPTIONS[5], new GoldenPelt());
        else
            this.imageEventText.setDialogOption(OPTIONS[4] + golden + OPTIONS[5], true, new GoldenPelt());

        if(gold >= knife)
            this.imageEventText.setDialogOption(OPTIONS[6] + knife + OPTIONS[7], new Knife());
        else
            this.imageEventText.setDialogOption(OPTIONS[6] + knife + OPTIONS[7], true, new Knife());

        this.imageEventText.setDialogOption(OPTIONS[8]);

    }

    @Override
    public void render(SpriteBatch sb) {
        this.imageEventText.render(sb);
    }

}
