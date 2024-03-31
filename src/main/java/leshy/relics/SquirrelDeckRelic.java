package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.util.TextureLoader;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class SquirrelDeckRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = LeshyMod.makeID(SquirrelDeckRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("squirrel_deck_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("squirrel_deck_relic.png"));

    public SquirrelDeckRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public void atTurnStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(LeshyMod.getSquirrel()));
    }

    @Override
    public void onRightClick() {

        if(!AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){

            for(AbstractCard c : AbstractDungeon.player.hand.group){
                if(c instanceof AbstractCreatureCard && ((AbstractCreatureCard) c).tribe == AbstractCreatureCard.CreatureTribe.SQUIRREL){
                    flash();
                    addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                    addToBot(new DrawCardAction(1));
                    return;
                }
            }

        }
    }

}


