package leshy.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import leshy.cards.abstracts.AbstractLeshyCard;


public class StaticCardPatch {


    @SpirePatch(clz = AbstractCard.class, method = "renderImage", paramtypez = {SpriteBatch.class, boolean.class, boolean.class})
    public static class Image{

        @SpirePrefixPatch
        public static SpireReturn Prefix(AbstractCard __instance, SpriteBatch sb, boolean hovered, boolean selected) {
            if(__instance instanceof AbstractLeshyCard && ((AbstractLeshyCard) __instance).isStatic){
                ((AbstractLeshyCard) __instance).renderStatic(sb);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(clz = AbstractCard.class, method = "renderTitle", paramtypez = {SpriteBatch.class})
    public static class Title{

        @SpirePrefixPatch
        public static SpireReturn Prefix(AbstractCard __instance, SpriteBatch sb) {
            if(__instance instanceof AbstractLeshyCard && ((AbstractLeshyCard) __instance).isStatic){
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }


    @SpirePatch(clz = AbstractCard.class, method = "renderDescription", paramtypez = {SpriteBatch.class})
    public static class Description{

        @SpirePrefixPatch
        public static SpireReturn Prefix(AbstractCard __instance, SpriteBatch sb) {
            if(__instance instanceof AbstractLeshyCard && ((AbstractLeshyCard) __instance).isStatic){
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(clz = AbstractCard.class, method = "renderDescriptionCN", paramtypez = {SpriteBatch.class})
    public static class DescriptionCN{

        @SpirePrefixPatch
        public static SpireReturn Prefix(AbstractCard __instance, SpriteBatch sb) {
            if(__instance instanceof AbstractLeshyCard && ((AbstractLeshyCard) __instance).isStatic){
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(clz = AbstractCard.class, method = "renderTint", paramtypez = {SpriteBatch.class})
    public static class Tint{

        @SpirePrefixPatch
        public static SpireReturn Prefix(AbstractCard __instance, SpriteBatch sb) {
            if(__instance instanceof AbstractLeshyCard && ((AbstractLeshyCard) __instance).isStatic){
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy", paramtypez = {SpriteBatch.class})
    public static class Energy{

        @SpirePrefixPatch
        public static SpireReturn Prefix(AbstractCard __instance, SpriteBatch sb) {
            if(__instance instanceof AbstractLeshyCard && ((AbstractLeshyCard) __instance).isStatic){
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(clz = AbstractCard.class, method = "renderCardTip", paramtypez = {SpriteBatch.class})
    public static class Tip{

        @SpirePrefixPatch
        public static SpireReturn Prefix(AbstractCard __instance, SpriteBatch sb) {
            if(__instance instanceof AbstractLeshyCard && ((AbstractLeshyCard) __instance).isStatic){
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }


}
