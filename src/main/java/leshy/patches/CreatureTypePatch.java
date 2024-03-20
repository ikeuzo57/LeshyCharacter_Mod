package leshy.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractLeshyCard;
import leshy.characters.Leshy;


public class CreatureTypePatch {

    @SpirePatch(clz = AbstractCard.class, method = "renderCardBg")
    public static class BackgroundPre{

        @SpirePrefixPatch
        public static void Prefix(AbstractCard __instance, SpriteBatch sb, float x, float y) {
            if(__instance instanceof AbstractCreatureCard){
                __instance.type = AbstractCard.CardType.SKILL;
            }
        }

    }

    @SpirePatch(clz = AbstractCard.class, method = "renderCardBg")
    public static class BackgroundPost{

        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance, SpriteBatch sb, float x, float y) {
            if(__instance instanceof AbstractCreatureCard){
                __instance.type = Leshy.Enums.CREATURE;
            }
        }

    }

    @SpirePatch(clz = AbstractCard.class, method = "renderPortraitFrame")
    public static class FramePre{

        @SpirePrefixPatch
        public static void Prefix(AbstractCard __instance, SpriteBatch sb, float x, float y) {
            if(__instance instanceof AbstractCreatureCard){
                __instance.type = AbstractCard.CardType.SKILL;
            }
        }

    }

    @SpirePatch(clz = AbstractCard.class, method = "renderPortraitFrame")
    public static class FramePost{

        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance, SpriteBatch sb, float x, float y) {
            if(__instance instanceof AbstractCreatureCard){
                __instance.type = Leshy.Enums.CREATURE;
            }
        }

    }

    @SpirePatch(clz = AbstractCard.class, method = "renderType")
    public static class Type{

        @SpirePrefixPatch
        public static SpireReturn Prefix(AbstractCard __instance, SpriteBatch sb) {

            if(__instance instanceof AbstractLeshyCard && ((AbstractLeshyCard) __instance).isStatic){
                return SpireReturn.Return();
            }


            if(__instance instanceof AbstractCreatureCard){
                BitmapFont font = FontHelper.cardTypeFont;
                font.getData().setScale(__instance.drawScale);

                Color renderColor = ReflectionHacks.getPrivate(__instance, AbstractCard.class, "renderColor");
                Color typeColor = new Color(0.35F, 0.35F, 0.35F, 0.0F);
                typeColor.a = renderColor.a;

                String msg;

                switch (((AbstractCreatureCard) __instance).tribe){
                    case AVIAN:
                        msg = "Avian";
                        break;
                    case AMALGAM:
                        msg = "Amalgam";
                        font.getData().setScale(__instance.drawScale * 0.85f);
                        break;
                    case CANINE:
                        msg = "Canine";
                        break;
                    case HOOVED:
                        msg = "Hooved";
                        break;
                    case ANT:
                    case INSECT:
                        msg = "Insect";
                        break;
                    case REPTILE:
                        msg = "Reptile";
                        break;
                    case SQUIRREL:
                        msg = "Squirrel";
                        font.getData().setScale(__instance.drawScale * 0.85f);
                        break;
                    default:
                        msg = "Creature";
                        font.getData().setScale(__instance.drawScale * 0.85f);
                        break;
                }

                FontHelper.renderRotatedText(sb, font, msg, __instance.current_x, __instance.current_y - 22.0F * __instance.drawScale * Settings.scale, 0.0F, -1.0F * __instance.drawScale * Settings.scale, __instance.angle, false, typeColor);


                //Mycologists Effects

                Color color = Color.WHITE.cpy();
                color.a = __instance.transparency;
                sb.setColor(color);

                if(((AbstractCreatureCard) __instance).mushroomCount > 1){

                    TextureAtlas.AtlasRegion cracks = LeshyMod.getLeshyAtlasRegion("cracks");
                    sb.draw(cracks, __instance.current_x + cracks.offsetX - cracks.originalWidth / 2.0F, __instance.current_y + cracks.offsetY - cracks.originalHeight / 2.0F, cracks.originalWidth / 2.0F - cracks.offsetX, cracks.originalHeight / 2.0F - cracks.offsetY, cracks.packedWidth, cracks.packedHeight, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle);

                    TextureAtlas.AtlasRegion stitches = LeshyMod.getLeshyAtlasRegion("stitches");
                    sb.draw(stitches, __instance.current_x + stitches.offsetX - stitches.originalWidth / 2.0F, __instance.current_y + stitches.offsetY - stitches.originalHeight / 2.0F, stitches.originalWidth / 2.0F - stitches.offsetX, stitches.originalHeight / 2.0F - stitches.offsetY, stitches.packedWidth, stitches.packedHeight, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle);

                    TextureAtlas.AtlasRegion bloodstain = LeshyMod.getLeshyAtlasRegion("bloodstain");
                    sb.draw(bloodstain, __instance.current_x + bloodstain.offsetX - bloodstain.originalWidth / 2.0F, __instance.current_y + bloodstain.offsetY - bloodstain.originalHeight / 2.0F, bloodstain.originalWidth / 2.0F - bloodstain.offsetX, bloodstain.originalHeight / 2.0F - bloodstain.offsetY, bloodstain.packedWidth, bloodstain.packedHeight, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle);

                }



                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }


}
