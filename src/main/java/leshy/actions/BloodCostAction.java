package leshy.actions;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import leshy.LeshyMod;
import leshy.cards.Starvation;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.Cat;
import leshy.cards.UndeadCat;
import leshy.orbs.CreatureOrb;
import leshy.powers.SacrificePower;
import leshy.relics.BloodBagRelic;
import leshy.relics.GrudgeRelic;

import java.util.ArrayList;

public class BloodCostAction extends AbstractGameAction {

    private AbstractPlayer p = AbstractDungeon.player;

    private ArrayList<AbstractCard> skip;

    private AbstractCard source;

    private final int cost;

    public BloodCostAction(int cost, AbstractCard source){
        this(cost, source, new ArrayList<>());
    }

    public BloodCostAction(int cost, AbstractCard source, ArrayList<AbstractCard> skip){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.cost = cost;
        this.skip = skip;
        this.source = source;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            int bloodAvailable = 0;
            for(AbstractOrb o : p.orbs){
                if(o instanceof CreatureOrb && !(((CreatureOrb) o).creatureCard instanceof Starvation)){

                    if(((CreatureOrb) o).creatureCard.bloodless && !((CreatureOrb) o).creatureCard.current.contains(AbstractCreatureCard.Sigils.WORTHY_SACRIFICE)){
                        continue;
                    }
                    boolean skipCard = false;
                    for(AbstractCard ac : skip){
                        if(ac.uuid.equals(((CreatureOrb) o).creatureCard.uuid)){
                            skipCard = true;
                        }
                    }
                    if(skipCard){
                        continue;
                    }

                    if(((CreatureOrb) o).creatureCard.current.contains(AbstractCreatureCard.Sigils.WORTHY_SACRIFICE)){
                        bloodAvailable += 3;
                    }else{
                        bloodAvailable ++;
                    }
                    AbstractCreatureCard card = (AbstractCreatureCard) ((CreatureOrb) o).creatureCard.makeSameInstanceOf();
                    card.orb = (CreatureOrb) o;
                    card.applyPowers();
                    tmp.addToBottom(card);
                }
            }

            if(bloodAvailable <= cost){

                if(LeshyMod.blood && cost > bloodAvailable)
                    addToBot(new LoseHPAction(p, p, (cost - bloodAvailable) * BloodBagRelic.LIFE_LOSS));

                for(AbstractOrb o : p.orbs){
                    if(o instanceof CreatureOrb && !(((CreatureOrb) o).creatureCard.bloodless && !((CreatureOrb) o).creatureCard.current.contains(AbstractCreatureCard.Sigils.WORTHY_SACRIFICE))  && !(((CreatureOrb) o).creatureCard instanceof Starvation)) {

                        for(AbstractPower ap : AbstractDungeon.player.powers){
                            if(ap instanceof SacrificePower)
                                ap.amount += 1;
                        }

                        if(!(((CreatureOrb) o).creatureCard.current.contains(AbstractCreatureCard.Sigils.MANY_LIVES) && ((CreatureOrb) o).creatureCard.livesLost < 8)) {
                            addToTop(new CreatureSacrificeAction((CreatureOrb) o));
                            if(((CreatureOrb) o).creatureCard instanceof Cat){
                                UndeadCat cat = new UndeadCat();
                                cat.transform(((CreatureOrb) o).creatureCard);
                                addToBot(new SummonCreatureAction(new CreatureOrb(cat)));
                            }
                            for(AbstractRelic r : AbstractDungeon.player.relics){
                                if(r instanceof GrudgeRelic){
                                    r.flash();
                                    ((CreatureOrb) o).creatureCard.isStatic = true;
                                    MultiCardPreview.clear(((CreatureOrb) o).creatureCard);
                                    break;
                                }
                            }
                        }else{
                            ((CreatureOrb) o).creatureCard.livesLost++;
                        }
                    }
                }
                this.isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmp, 1, "Blood Cost Remaining " + cost, false);
            tickDuration();

            return;

        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            c.applyPowers();
            c.unhover();

            if(c instanceof AbstractCreatureCard) {

                for(AbstractPower ap : AbstractDungeon.player.powers){
                    if(ap instanceof SacrificePower)
                        ap.amount += 1;
                }

                int newCost = cost;
                if (((AbstractCreatureCard) c).current.contains(AbstractCreatureCard.Sigils.WORTHY_SACRIFICE)) {
                    newCost -= 3;
                } else {
                    newCost--;
                }

                if(!(((AbstractCreatureCard) c).current.contains(AbstractCreatureCard.Sigils.MANY_LIVES) && ((AbstractCreatureCard) c).livesLost < 8)){
                    addToTop(new CreatureSacrificeAction(((AbstractCreatureCard) c).orb));
                    if(c instanceof Cat){
                        UndeadCat cat = new UndeadCat();
                        cat.transform((AbstractCreatureCard) c);
                        addToBot(new SummonCreatureAction(new CreatureOrb(cat)));
                    }
                    for(AbstractRelic r : AbstractDungeon.player.relics){
                        if(r instanceof GrudgeRelic){
                            r.flash();
                            ((AbstractCreatureCard) c).orb.creatureCard.isStatic = true;
                            MultiCardPreview.clear(((AbstractCreatureCard) c).orb.creatureCard);
                            break;
                        }
                    }
                }else{
                    ((AbstractCreatureCard) c).orb.creatureCard.livesLost++;
                }

                if(newCost > 0){
                    this.skip.add(c);
                    addToTop(new BloodCostAction(newCost, source, skip));
                }

            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.isDone = true;

        }

        tickDuration();

    }

}













