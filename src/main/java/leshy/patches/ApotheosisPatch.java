package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import javassist.CtBehavior;
import leshy.cards.abstracts.RightClickCard;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

import java.util.ArrayList;


@SpirePatch(clz = ApotheosisAction.class, method = "update")
public class ApotheosisPatch {

    @SpirePrefixPatch
    public static void patch(ApotheosisAction __instance) {

        if(AbstractDungeon.player instanceof Leshy){
            for(AbstractOrb o : AbstractDungeon.player.orbs){
                if(o instanceof CreatureOrb){
                    ((CreatureOrb) o).creatureCard.upgrade();
                    ((CreatureOrb) o).creatureCard.applyPowers();
                }
            }
        }

    }

    public static class Locator extends SpireInsertLocator{
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "player");
            return LineFinder.findInOrder(ctBehavior, fieldAccessMatcher);
        }
    }

}




