package leshy.rooms;

import basemod.CustomEventRoom;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.events.MycologistsEvent;

public class MycologistsRoom extends AbstractRoom {

    public MycologistsRoom(){

        String iconPath = "leshyResources/images/ui/map/mycologist.png";
        String outlinePath = "leshyResources/images/ui/map/mycologistOutline.png";

        this.phase = AbstractRoom.RoomPhase.EVENT;

        setMapImg(ImageMaster.loadImage(iconPath), ImageMaster.loadImage(outlinePath));
        setMapSymbol("Y");

    }


    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event = new MycologistsEvent();
        this.event.onEnterRoom();
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp)
            this.event.update();
        if (this.event.waitTimer == 0.0F && !this.event.hasFocus &&
                this.phase != AbstractRoom.RoomPhase.COMBAT) {
            this.phase = AbstractRoom.RoomPhase.COMPLETE;
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
