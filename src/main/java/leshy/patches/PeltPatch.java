package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import javassist.CtBehavior;
import leshy.cards.abstracts.RightClickCard;

import java.util.ArrayList;




@SpirePatch(clz = MasterDeckViewScreen.class, method = "updatePositions")
public class PeltPatch {

    @SpireInsertPatch(locator = PeltPatch.Locator.class, localvars = {"cards", "i"})
    public static void patch(MasterDeckViewScreen __instance, ArrayList<AbstractCard> cards, int i) {

        if(cards.get(i) instanceof RightClickCard){
            ((RightClickCard) cards.get(i)).clickUpdate();
        }

    }

    public static class Locator extends SpireInsertLocator{
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "target_x");
            return LineFinder.findInOrder(ctBehavior, fieldAccessMatcher);
        }
    }

}




