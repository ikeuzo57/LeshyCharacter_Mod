package leshy.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.LeshyMod;
import leshy.characters.Leshy;
import leshy.orbs.CreatureOrb;

@SpirePatch(clz = PotionHelper.class, method = "initialize")
public class RemovePotionsPatch {

    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer.PlayerClass chosenClass) {

        if(chosenClass == Leshy.Enums.LESHY){

            PotionHelper.potions.remove("AttackPotion");

        }

    }


}
