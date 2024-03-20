package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import leshy.LeshyMod;
import leshy.characters.Leshy;

@SpirePatch(clz = AbstractDungeon.class, method = "initializeRelicList")
public class RemoveRelicsPatch {

    @SpirePrefixPatch
    public static void Prefix(AbstractDungeon obj) {

        if(AbstractDungeon.player.chosenClass == Leshy.Enums.LESHY){

            AbstractDungeon.relicsToRemoveOnStart.add("Akabeko");
            AbstractDungeon.relicsToRemoveOnStart.add("Ancient Tea Set");
            AbstractDungeon.relicsToRemoveOnStart.add("Art of War");
            AbstractDungeon.relicsToRemoveOnStart.add("Happy Flower");
            AbstractDungeon.relicsToRemoveOnStart.add("Juzu Bracelet");
            AbstractDungeon.relicsToRemoveOnStart.add("Lantern");
            AbstractDungeon.relicsToRemoveOnStart.add("Nunchaku");
            AbstractDungeon.relicsToRemoveOnStart.add("Orichalcum");
            AbstractDungeon.relicsToRemoveOnStart.add("Pen Nib");
            AbstractDungeon.relicsToRemoveOnStart.add("Tiny Chest");
            AbstractDungeon.relicsToRemoveOnStart.add("War Paint");
            AbstractDungeon.relicsToRemoveOnStart.add("Whetstone");

            AbstractDungeon.relicsToRemoveOnStart.add("Blue Candle");
            AbstractDungeon.relicsToRemoveOnStart.add("Bottled Flame");
            AbstractDungeon.relicsToRemoveOnStart.add("Gremlin Horn");
            AbstractDungeon.relicsToRemoveOnStart.add("Kunai");
            AbstractDungeon.relicsToRemoveOnStart.add("Letter Opener");
            AbstractDungeon.relicsToRemoveOnStart.add("Molten Egg 2");
            AbstractDungeon.relicsToRemoveOnStart.add("Mummified Hand");
            AbstractDungeon.relicsToRemoveOnStart.add("Ornamental Fan");
            AbstractDungeon.relicsToRemoveOnStart.add("Shuriken");
            AbstractDungeon.relicsToRemoveOnStart.add("StrikeDummy");
            AbstractDungeon.relicsToRemoveOnStart.add("Sundial");


            AbstractDungeon.relicsToRemoveOnStart.add("Calipers");
            AbstractDungeon.relicsToRemoveOnStart.add("Ice Cream");

            AbstractDungeon.relicsToRemoveOnStart.add("OrangePellets");

            AbstractDungeon.relicsToRemoveOnStart.add("Pandora's Box");

        }

    }


}
