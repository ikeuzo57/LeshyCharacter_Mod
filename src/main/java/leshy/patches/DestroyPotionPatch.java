package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import leshy.characters.Leshy;
import leshy.potions.ToothPotion;
import leshy.potions.interfaces.OnDestroyPotion;

@SpirePatch(clz = TopPanel.class, method = "destroyPotion", paramtypez = {int.class})
public class DestroyPotionPatch {

    @SpirePostfixPatch
    public static void Prefix(TopPanel __instance, int slot) {

        if(AbstractDungeon.player.potions.get(slot) instanceof OnDestroyPotion)
            ((OnDestroyPotion) AbstractDungeon.player.potions.get(slot)).onDestroy();

    }


}
