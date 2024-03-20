package leshy.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.events.*;

public class LeshyEventRoom extends AbstractRoom {


    public LeshyEventRoom(){

        String iconPath = "leshyResources/images/ui/map/event.png";
        String outlinePath = "leshyResources/images/ui/map/eventOutline.png";

        this.phase = RoomPhase.EVENT;

        setMapImg(ImageMaster.loadImage(iconPath), ImageMaster.loadImage(outlinePath));
        setMapSymbol("?");

    }


    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event = getLeshyEvent();
        this.event.onEnterRoom();
    }


    public AbstractEvent getLeshyEvent(){

        float roll = AbstractDungeon.eventRng.random();

        float mycologistsChance;
        float boneAltarChance;
        float trapperChance;
        float woodcarverChance;
        float goobertChance;
        float packChance;

        mycologistsChance = 0.1F;
        boneAltarChance = 0.15F;
        trapperChance = 0.15F;
        woodcarverChance = 0.15F;
        goobertChance = 0.2F;
        packChance = 1F - (boneAltarChance + trapperChance + goobertChance); //0.25F

        LeshyMod.logger.info("Event Chances:");
        LeshyMod.logger.info("Mycologists : "+ mycologistsChance);
        LeshyMod.logger.info("Bone Altar : "+ boneAltarChance);
        LeshyMod.logger.info("Trapper : " + trapperChance);
        LeshyMod.logger.info("Woodcarver : " + woodcarverChance);
        LeshyMod.logger.info("Goobert : " + goobertChance);
        LeshyMod.logger.info("Pack : " + packChance);

        LeshyMod.logger.info("Mycologists : " + roll + " vs " + mycologistsChance);
        if(roll < mycologistsChance)
            return new MycologistsEvent();
        else
            roll -= mycologistsChance;

        LeshyMod.logger.info("Bone Altar : " + roll + " vs " + boneAltarChance);
        if(roll < boneAltarChance)
            return new BoneAltarEvent();
        else
            roll -= boneAltarChance;

        LeshyMod.logger.info("Trapper : " + roll + " vs " + trapperChance);
        if(roll < trapperChance)
            return new TrapperEvent();
        else
            roll -= trapperChance;

        LeshyMod.logger.info("Woodcarver : " + roll + " vs " + woodcarverChance);
        if(roll < woodcarverChance)
            return new WoodcarverEvent();
        else
            roll -= woodcarverChance;

        LeshyMod.logger.info("Goobert : " + roll + " vs " + goobertChance);
        if(roll < goobertChance)
            return new GoobertEvent();
        else
            roll -= goobertChance;

        LeshyMod.logger.info("Pack : " + roll + " vs " + packChance);
        if(roll < packChance)
            return new PackEvent();


        LeshyMod.logger.info("Something went wrong.");
        return getLeshyEvent();

    }


    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp)
            this.event.update();
        if (this.event.waitTimer == 0.0F && !this.event.hasFocus &&
                this.phase != RoomPhase.COMBAT) {
            this.phase = RoomPhase.COMPLETE;
            this.event.reopen();
        }
    }

    public void render(SpriteBatch sb) {
        if (this.event != null)
            this.event.render(sb);
        super.render(sb);
    }

    public void renderAboveTopPanel(SpriteBatch sb) {
        super.renderAboveTopPanel(sb);
        if (this.event != null)
            this.event.renderAboveTopPanel(sb);
    }



}
