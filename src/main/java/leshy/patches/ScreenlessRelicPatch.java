package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.defect.TriggerEndOfTurnOrbsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.*;
import leshy.LeshyMod;
import leshy.relics.*;

import java.util.Objects;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnRandomRelicKey;

@SpirePatch(clz = AbstractDungeon.class, method = "returnRandomScreenlessRelic", paramtypez = {AbstractRelic.RelicTier.class})
public class ScreenlessRelicPatch {

    @SpirePrefixPatch
    public static SpireReturn<AbstractRelic> Prefix(AbstractRelic.RelicTier tier) {

        LeshyMod.logger.info("Returning " + tier.name() + " relic");
        AbstractRelic tmpRelic = RelicLibrary.getRelic(returnRandomRelicKey(tier)).makeCopy();
        while (Objects.equals(tmpRelic.relicId, "Bottled Flame") || Objects.equals(tmpRelic.relicId, "Bottled Lightning") ||
                Objects.equals(tmpRelic.relicId, "Bottled Tornado") || Objects.equals(tmpRelic.relicId, "Whetstone") ||
                tmpRelic instanceof RedMushroomRelic || tmpRelic instanceof TrenchCoatRelic || tmpRelic instanceof GreatKrakenRelic || tmpRelic instanceof BottledOpossumRelic)
            tmpRelic = RelicLibrary.getRelic(returnRandomRelicKey(tier)).makeCopy();

        return SpireReturn.Return(tmpRelic);

    }


}
