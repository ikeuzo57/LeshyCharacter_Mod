package leshy.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.abstracts.AbstractLeshyCard;

@SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
public class RenderCreaturePatch {

    @SpirePostfixPatch
    public static void Postfix(AbstractCard obj, SpriteBatch sb) {

        if(obj instanceof AbstractLeshyCard && ((AbstractLeshyCard) obj).isStatic){
            return;
        }

        if(obj instanceof AbstractCreatureCard){

            Color color = Color.WHITE.cpy();
            color.a = obj.transparency;
            sb.setColor(color);


            FontHelper.cardEnergyFont_L.getData().setScale(obj.drawScale);
            BitmapFont font = FontHelper.cardEnergyFont_L;

            if (((AbstractCreatureCard) obj).costType != AbstractCreatureCard.CreatureCostType.NONE && ((AbstractCreatureCard) obj).extraCost != 0) {

                TextureAtlas.AtlasRegion cost;
                if (((AbstractCreatureCard) obj).costType == AbstractCreatureCard.CreatureCostType.BLOOD)
                    cost = LeshyMod.getLeshyAtlasRegion("blood");
                else
                    cost = LeshyMod.getLeshyAtlasRegion("bone");

                sb.draw(cost, obj.current_x + cost.offsetX - cost.originalWidth / 2.0F, obj.current_y + cost.offsetY - cost.originalHeight / 2.0F, cost.originalWidth / 2.0F - cost.offsetX, cost.originalHeight / 2.0F - cost.offsetY, cost.packedWidth, cost.packedHeight, obj.drawScale * Settings.scale, obj.drawScale * Settings.scale, obj.angle);


                Color costColor = Color.WHITE.cpy();
                if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(obj) && !obj.canUse(AbstractDungeon.player, null)) {
                    costColor = new Color(1.0F, 0.3F, 0.3F, 1.0F);
                } else if (obj.isCostModified || obj.isCostModifiedForTurn || obj.freeToPlay()) {
                    costColor = new Color(0.4F, 1.0F, 0.4F, 1.0F);
                }
                costColor.a = obj.transparency;
                String costText = "" + ((AbstractCreatureCard) obj).extraCost;

                FontHelper.renderRotatedText(sb, font, costText, obj.current_x, obj.current_y, -132.0F * obj.drawScale * Settings.scale, 192.0F * obj.drawScale * Settings.scale, obj.angle, false, costColor);

            }

            TextureAtlas.AtlasRegion attack = LeshyMod.getLeshyAtlasRegion("attack");
            sb.draw(attack, obj.current_x + attack.offsetX - attack.originalWidth / 2.0F, obj.current_y + attack.offsetY - attack.originalHeight / 2.0F, attack.originalWidth / 2.0F - attack.offsetX, attack.originalHeight / 2.0F - attack.offsetY, attack.packedWidth, attack.packedHeight, obj.drawScale * Settings.scale, obj.drawScale * Settings.scale, obj.angle);


            Color attackColor = Color.WHITE.cpy();
            if (((AbstractCreatureCard) obj).isAttackModified) {
                if (((AbstractCreatureCard) obj).attack >= ((AbstractCreatureCard) obj).baseAttack)
                    attackColor = new Color(0.4F, 1.0F, 0.4F, 1.0F);
                else
                    attackColor = new Color(1.0F, 0.3F, 0.3F, 1.0F);
            }
            attackColor.a = obj.transparency;
            String attackText = "" + ((AbstractCreatureCard) obj).attack;


            FontHelper.renderRotatedText(sb, font, attackText, obj.current_x, obj.current_y, -103.0F * obj.drawScale * Settings.scale, 3.0F * obj.drawScale * Settings.scale, obj.angle, false, attackColor);


            TextureAtlas.AtlasRegion health = LeshyMod.getLeshyAtlasRegion("health");
            sb.draw(health, obj.current_x + health.offsetX - health.originalWidth / 2.0F, obj.current_y + health.offsetY - health.originalHeight / 2.0F, health.originalWidth / 2.0F - health.offsetX, health.originalHeight / 2.0F - health.offsetY, health.packedWidth, health.packedHeight, obj.drawScale * Settings.scale, obj.drawScale * Settings.scale, obj.angle);


            Color healthColor = Color.WHITE.cpy();
            if (((AbstractCreatureCard) obj).isHealthModified) {
                if (((AbstractCreatureCard) obj).health >= ((AbstractCreatureCard) obj).baseHealth)
                    healthColor = new Color(0.4F, 1.0F, 0.4F, 1.0F);
                else
                    healthColor = new Color(1.0F, 0.3F, 0.3F, 1.0F);
            }
            healthColor.a = obj.transparency;
            int cardHealth = ((AbstractCreatureCard) obj).health;
            if (((AbstractCreatureCard) obj).orb != null && ((AbstractCreatureCard) obj).orb.damageTaken > 0) {
                cardHealth -= ((AbstractCreatureCard) obj).orb.damageTaken;
                healthColor = new Color(1.0F, 0.3F, 0.3F, 1.0F);
            }
            String healthText = "" + cardHealth;

            FontHelper.renderRotatedText(sb, font, healthText, obj.current_x, obj.current_y, +108.0F * obj.drawScale * Settings.scale, 3.0F * obj.drawScale * Settings.scale, obj.angle, false, healthColor);


        }






    }


}
