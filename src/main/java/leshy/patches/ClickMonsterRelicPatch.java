package leshy.patches;

import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import leshy.LeshyMod;
import leshy.relics.BottledOpossumRelic;
import leshy.relics.GreatKrakenRelic;
import leshy.relics.RedMushroomRelic;
import leshy.relics.TrenchCoatRelic;
import leshy.relics.interfaces.ClickMonsterRelic;

import java.util.Objects;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnRandomRelicKey;

@SpirePatch(clz = AbstractMonster.class, method = "update")
public class ClickMonsterRelicPatch {

    @SpirePrefixPatch
    public static void Postfix(AbstractMonster __instance) {

        if(((Boolean)HitboxRightClick.rightClickStarted.get(__instance.hb)).booleanValue() && __instance.hb.hovered && InputHelper.justReleasedClickRight){

            if(!__instance.isDead && !__instance.isEscaping && !__instance.isDying && __instance.currentHealth > 0){

                for(AbstractRelic r : AbstractDungeon.player.relics){
                    if(r instanceof ClickMonsterRelic){
                        ((ClickMonsterRelic) r).onClick(__instance);
                    }
                }

            }

        }

    }


}
