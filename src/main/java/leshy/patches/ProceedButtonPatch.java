package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import leshy.characters.Leshy;

import java.util.ArrayList;

//ProceedButton expects us to be Done And Moving On when we click on it.  override that.
@SpirePatch(clz = ProceedButton.class, method = "update", paramtypez = {})
public class ProceedButtonPatch {
    @SpireInsertPatch(locator=Locator.class)
    public static SpireReturn<Void> update() {
        //after button is clicked, but before it sets screen to complete...
        if (CardCrawlGame.dungeon != null && AbstractDungeon.player instanceof Leshy) {
            if(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.neow.NeowRoom) {
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.screen=AbstractDungeon.CurrentScreen.NONE;
                AbstractDungeon.overlayMenu.hideBlackScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                return SpireReturn.Return();
            }
        }
        return SpireReturn.Continue();
    }
    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.InstanceOfMatcher(NeowRoom.class);
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}