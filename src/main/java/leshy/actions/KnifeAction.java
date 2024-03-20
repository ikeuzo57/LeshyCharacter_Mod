package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import leshy.cards.GoldenPelt;
import leshy.cards.RabbitPelt;
import leshy.cards.WolfPelt;
import leshy.relics.GoatEyeRelic;
import leshy.relics.MagicEyeRelic;

public class KnifeAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    private DamageInfo info;

    public KnifeAction(AbstractCreature target, DamageInfo info){
        this.info = info;
        setValues(target, info);
        this.duration = 0.1F;
        this.actionType = ActionType.DAMAGE;
    }
    
    @Override
    public void update() {

        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));

            this.target.damage(this.info);

            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {

                if(this.target instanceof AbstractMonster){
                    switch (((AbstractMonster) this.target).type){
                        case BOSS:
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new GoldenPelt(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                            break;
                        case ELITE:
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new WolfPelt(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                            break;
                        case NORMAL:
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new RabbitPelt(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                            break;
                    }
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













