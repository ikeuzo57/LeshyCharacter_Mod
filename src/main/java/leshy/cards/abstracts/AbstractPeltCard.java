package leshy.cards.abstracts;

import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import leshy.actions.SummonCreatureAction;
import leshy.cards.PeltLice;
import leshy.orbs.CreatureOrb;
import leshy.relics.InfestedPeltRelic;
import leshy.vfx.SellPeltEffect;

public abstract class AbstractPeltCard extends AbstractCreatureCard implements RightClickCard{

    public boolean sold;

    public AbstractPeltCard(final String id,
                            final String img,
                            final int cost,
                            final CardType type,
                            final CardColor color,
                            final CardRarity rarity,
                            final CardTarget target) {

        super(id, img, cost, type, color, rarity, target);

        this.bloodless = true;

        this.sold = false;

        tribe = CreatureTribe.NONE;

        costType = CreatureCostType.NONE;
        extraCost = 0;

    }


    public void onRightClick(){

        if(!sold){
            sold = true;
            AbstractDungeon.effectsQueue.add(new SellPeltEffect(this));
        }

    }


    public void clickUpdate(){

        if (AbstractDungeon.getCurrRoom() instanceof ShopRoom && (HitboxRightClick.rightClicked.get(this.hb)).booleanValue())
            onRightClick();

    }

    @Override
    public void onSummon() {
        super.onSummon();
        for(AbstractRelic ar : AbstractDungeon.player.relics) {
            if (ar instanceof InfestedPeltRelic) {
                addToBot(new SummonCreatureAction(new CreatureOrb(new PeltLice())));
            }
        }
    }

    /*
    @Override
    public void applyPowers() {

        for(AbstractRelic ar : AbstractDungeon.player.relics) {
            if (ar instanceof InfestedPeltRelic) {
                baseFleeting = false;
                break;
            }
        }

        super.applyPowers();

        baseFleeting = true;

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        for(AbstractRelic ar : AbstractDungeon.player.relics) {
            if (ar instanceof InfestedPeltRelic) {
                baseFleeting = false;
                break;
            }
        }

        super.calculateCardDamage(mo);

        baseFleeting = true;

    }
    */

    @Override
    public String extraText() {
        return "leshy:Pelt !leshy:SecondMagic!";
    }
}