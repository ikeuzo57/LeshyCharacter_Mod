package leshy.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;
import leshy.cards.abstracts.AbstractCreatureCard;


@SpirePatch(clz = AbstractCard.class, method = "renderTitle", paramtypez = {SpriteBatch.class})
public class RenderTitlePatch {

    @SpireInsertPatch(locator = RenderTitlePatch.Locator.class)
    public static void patch(AbstractCard __instance, SpriteBatch sb) {

        if(__instance instanceof AbstractCreatureCard){
            FontHelper.cardTitleFont.getData().setScale(__instance.drawScale * ((AbstractCreatureCard) __instance).titleScale);
        }

    }

    public static class Locator extends SpireInsertLocator{
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "upgraded");
            return LineFinder.findInOrder(ctBehavior, fieldAccessMatcher);
        }
    }

}




