package leshy.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FusionHammer;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import java.util.HashSet;

@SpirePatch(clz = AbstractRelic.class, method = "initializeTips")
public class EnergyRelicPatch {

    public static final HashSet<String> ENERGY_RELICS = getEnergyRelics();

    public static HashSet<String> getEnergyRelics(){
        HashSet<String> set = new HashSet<>();
        set.add("Busted Crown");
        set.add("Coffee Dripper");
        set.add("Cursed Key");
        set.add("Ectoplasm");
        set.add("Fusion Hammer");
        set.add("Philosopher's Stone");
        set.add("Runic Dome");
        set.add("SlaversCollar");
        set.add("Sozu");
        set.add("Velvet Choker");
        return set;
    }

    @SpirePrefixPatch
    public static void Prefix(AbstractRelic obj) {

        if(AbstractDungeon.player instanceof Leshy && ENERGY_RELICS.contains(obj.relicId)){
            PowerTip cons = AbstractCreatureCard.getPowerTip(AbstractCreatureCard.MISC_DESCRIPTION[13]);
            if(cons != null)
                obj.tips.add(cons);
        }

    }








}

