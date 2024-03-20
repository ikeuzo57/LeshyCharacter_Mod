package leshy.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.CtBehavior;
import leshy.LeshyMod;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

import java.util.ArrayList;


public class OrbDamagePatch {

    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class ShopCreatureCards{

        @SpireInsertPatch(locator = OrbDamagePatch.Locator.class, localvars = {"damageAmount"})
        public static void OrbDamage(AbstractPlayer __instance, DamageInfo info, @ByRef int[] damageAmount) {
            for(AbstractOrb o : __instance.orbs){
                if(o instanceof CreatureOrb){
                    damageAmount[0] = ((CreatureOrb) o).onAttackedToChangeDamage(info, damageAmount[0]);
                }
            }
        }

    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "onAttackedToChangeDamage");

            int[] line = LineFinder.findInOrder(ctBehavior, methodCallMatcher);

            line[0] += 2;

            return line;
        }
    }

}
