package leshy.potions;

import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import leshy.LeshyMod;
import leshy.actions.MoveOrbSlotAction;
import leshy.orbs.CreatureOrb;

public class WiseclockPotion extends AbstractPotion {


    public static final String POTION_ID = LeshyMod.makeID(WiseclockPotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public WiseclockPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOLT, PotionColor.POWER);
        
        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();
        
        // Initialize the Description

        description = DESCRIPTIONS[0];
        
       // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;
        
        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        
    }

    @Override
    public void use(AbstractCreature target) {

        if(AbstractDungeon.player.orbs.get(0) instanceof CreatureOrb){

            addToBot(new MoveOrbSlotAction((CreatureOrb)AbstractDungeon.player.orbs.get(0), AbstractDungeon.player.orbs.size()-1));

        }

    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new WiseclockPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 1;
    }

    public void upgradePotion(){
    }
}
