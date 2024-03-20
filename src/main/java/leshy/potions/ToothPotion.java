package leshy.potions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import leshy.LeshyMod;
import leshy.actions.MoveOrbSlotAction;
import leshy.orbs.CreatureOrb;
import leshy.potions.interfaces.OnDestroyPotion;

public class ToothPotion extends AbstractPotion implements OnDestroyPotion {


    public static final String POTION_ID = LeshyMod.makeID(ToothPotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ToothPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.PLACEHOLDER, PotionSize.SPIKY, PotionColor.WHITE);
        
        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();
        
        // Initialize the Description

        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1] + potency + DESCRIPTIONS[2];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = true;
        this.targetRequired = true;
        
        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        
    }

    @Override
    public void use(AbstractCreature target) {

        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.potency, DamageInfo.DamageType.THORNS);
        info.applyEnemyPowersOnly(target);
        addToBot(new DamageAction(target, info, AbstractGameAction.AttackEffect.BLUNT_LIGHT));

    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new ToothPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 20;
    }

    public void upgradePotion(){
        potency += 20;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void onDestroy() {
        AbstractDungeon.player.gainGold(this.potency);
    }
}
