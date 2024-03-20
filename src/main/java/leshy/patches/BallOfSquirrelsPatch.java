package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import leshy.relics.BallOfSquirrelsRelic;

@SpirePatch(clz = BossRelicSelectScreen.class, method = "relicObtainLogic", paramtypez = {AbstractRelic.class})
public class BallOfSquirrelsPatch {

    @SpirePostfixPatch
    public static void Postfix(BossRelicSelectScreen __instance, AbstractRelic r) {

        if(r instanceof BallOfSquirrelsRelic){
            r.instantObtain(AbstractDungeon.player, 1, true);
            (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
        }

    }


}
