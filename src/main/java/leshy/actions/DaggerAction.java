package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import leshy.relics.GoatEyeRelic;
import leshy.relics.MagicEyeRelic;

public class DaggerAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private DamageInfo info;

    public DaggerAction(AbstractCreature target, DamageInfo info){
        this.info = info;
        setValues(target, info);
        this.duration = 0.1F;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
    }
    
    @Override
    public void update() {

        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

            this.target.damage(this.info);

            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {

                boolean hasMagicEye = false;
                boolean hasGoatEye = false;
                for(AbstractRelic r : p.relics){
                    if(r instanceof MagicEyeRelic){
                        hasMagicEye = true;
                    }else if(r instanceof GoatEyeRelic){
                        hasGoatEye = true;
                        break;
                    }
                }
                if(!hasMagicEye){
                    MagicEyeRelic eye = new MagicEyeRelic();
                    eye.instantObtain();
                    CardCrawlGame.metricData.addRelicObtainData(eye);
                }else if(!hasGoatEye){
                    GoatEyeRelic eye = new GoatEyeRelic();
                    eye.instantObtain();
                    CardCrawlGame.metricData.addRelicObtainData(eye);
                }

                if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
            }
            tickDuration();
        }


        this.isDone = true;

    }

}













