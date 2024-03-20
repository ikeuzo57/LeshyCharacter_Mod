package leshy.cards.abstracts;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.actions.TotemBaseAction;

import java.util.ArrayList;

public class AbstractTotemBaseCard extends AbstractDynamicCard implements SpawnModificationCard{

    public float spawnRate = 0.8F;

    protected static final int COST = 1;

    public AbstractCreatureCard.Sigils base = null;

    public AbstractTotemBaseCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);

        upgradeDescription = true;
    }


    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        return AbstractDungeon.cardRng.randomBoolean(spawnRate * spawnMulti());
    }

    @Override
    public boolean canSpawnShop(ArrayList<AbstractCard> currentShopCards) {
        return AbstractDungeon.cardRng.randomBoolean(spawnRate);
    }

    public float spawnMulti(){
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if(c instanceof AbstractTotemBaseCard)
                return 0.5F;
        }
        return 1F;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TotemBaseAction(this));
        addToBot(new DrawCardAction(1));
    }

    @Override
    public void upgrade() {
        isInnate = true;
        super.upgrade();
    }
}
