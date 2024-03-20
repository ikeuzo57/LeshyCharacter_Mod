package leshy.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.events.BoneAltarEvent;
import leshy.events.MycologistsEvent;
import leshy.events.MysteriousStonesEvent;

public class MysteriousStonesRoom extends AbstractRoom {

    public MysteriousStonesRoom(){

        String iconPath = "leshyResources/images/ui/map/sigil.png";
        String outlinePath = "leshyResources/images/ui/map/sigilOutline.png";

        this.phase = RoomPhase.EVENT;

        setMapImg(ImageMaster.loadImage(iconPath), ImageMaster.loadImage(outlinePath));
        setMapSymbol("S");

    }


    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event = new MysteriousStonesEvent();
        this.event.onEnterRoom();
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
