package leshy.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.TheMoon;
import leshy.powers.TargetPower;

import java.util.ArrayList;

public class CreatureAttackAction extends AbstractGameAction {

    private final AbstractPlayer p = AbstractDungeon.player;

    AbstractCreatureCard card;

    private static final int POISON_AMOUNT = 1;

    public CreatureAttackAction(AbstractCreatureCard card){
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DAMAGE;
        this.card = card;

    }
    
    @Override
    public void update() {

        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        if(this.card.attack >= 10){
            effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        }

        if(this.card instanceof TheMoon){
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(m.currentHealth > 0 && !m.isDying && !m.isEscaping){
                    addToTop(new MoonExecuteAction(m, this.card.secondMagicNumber));
                }
                attackMonster(card, m, effect);
            }
            this.isDone = true;
            return;
        }


        ArrayList<AbstractMonster> randomMonsters = getRandomMonsters();

        if(randomMonsters.isEmpty()){
            this.isDone = true;
            return;
        }


        if(!this.card.current.contains(AbstractCreatureCard.Sigils.DOUBLE_STRIKE) && !this.card.current.contains(AbstractCreatureCard.Sigils.BIFURCATED) && !this.card.current.contains(AbstractCreatureCard.Sigils.TRIFURCATED)){

            attackMonster(card, randomMonsters.get(0), effect);

        }else if(this.card.current.contains(AbstractCreatureCard.Sigils.DOUBLE_STRIKE) && !this.card.current.contains(AbstractCreatureCard.Sigils.BIFURCATED) && !this.card.current.contains(AbstractCreatureCard.Sigils.TRIFURCATED)){

            for(int i=0; i<2; i++)
                attackMonster(card, randomMonsters.get(0), effect);

        }else{

            if(this.card.current.contains(AbstractCreatureCard.Sigils.TRIFURCATED))
                for(AbstractMonster am : randomMonsters)
                    attackMonster(card, am, effect);


            if(this.card.current.contains(AbstractCreatureCard.Sigils.DOUBLE_STRIKE) && this.card.current.contains(AbstractCreatureCard.Sigils.BIFURCATED)){

                for(AbstractMonster am : randomMonsters)
                    attackMonster(card, am, effect);

            }else{

                if(this.card.current.contains(AbstractCreatureCard.Sigils.DOUBLE_STRIKE))
                    attackMonster(card, randomMonsters.get(0), effect);

                if(this.card.current.contains(AbstractCreatureCard.Sigils.BIFURCATED))
                    for(int i=0; i<2; i++){
                        if(i >= randomMonsters.size())
                            break;
                        attackMonster(card, randomMonsters.get(i), effect);
                    }

            }

        }





        this.isDone = true;

    }

    public void attackMonster(AbstractCreatureCard card, AbstractMonster am, AbstractGameAction.AttackEffect effect){

        double vulnMulti = 1.0;
        for(AbstractPower ap : am.powers) {
            if (ap instanceof VulnerablePower) {
                vulnMulti *= 1.5;
            }
            if (ap instanceof FlightPower) {
                vulnMulti *= 0.5;
            }
        }
        int lifeLoss = (int) ((double) card.attack * vulnMulti);
        if(LeshyMod.sapling && card.tribe == AbstractCreatureCard.CreatureTribe.NONE){
            int lifeLoss_Health = (int) ((double) (card.health - card.orb.damageTaken) * vulnMulti);
            if(lifeLoss_Health > lifeLoss)
                lifeLoss = lifeLoss_Health;
        }

        if(card.current.contains(AbstractCreatureCard.Sigils.TOUCH_OF_DEATH)) {
            addToTop(new ApplyPowerAction(am, AbstractDungeon.player, new VulnerablePower(am, POISON_AMOUNT, false), POISON_AMOUNT));
        }
        if(card.current.contains(AbstractCreatureCard.Sigils.AIRBORNE)){
            addToTop(new LoseHPAction(am, AbstractDungeon.player, lifeLoss));
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(am.hb.cX, am.hb.cY, effect, false));
        }else{
            addToTop(new DamageAction(am, new DamageInfo(AbstractDungeon.player, lifeLoss, DamageInfo.DamageType.NORMAL), effect));
        }
    }


    public static ArrayList<AbstractMonster> getRandomMonsters(){
        ArrayList<AbstractMonster> randomMonsters = new ArrayList<>();
        ArrayList<AbstractMonster> target = new ArrayList<>();
        ArrayList<AbstractMonster> notTarget = new ArrayList<>();
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if(m.currentHealth > 0 && !m.isDying && !m.isEscaping){
                boolean isTarget = false;
                for(AbstractPower ap : m.powers){
                    if(ap instanceof TargetPower){
                        isTarget = true;
                        target.add(m);
                        break;
                    }
                }
                if(!isTarget)
                    notTarget.add(m);
            }
        }

        if(!target.isEmpty()){
            randomMonsters.add(target.get(0));
        }
        while(randomMonsters.size() < 3 && !notTarget.isEmpty()){
            int rng = AbstractDungeon.monsterRng.random(0, notTarget.size()-1);
            randomMonsters.add(notTarget.get(rng));
            notTarget.remove(rng);
        }
        return randomMonsters;
    }

}













