package leshy.cards.abstracts;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import leshy.relics.OldDataRelic;

import static leshy.LeshyMod.makeCardPath;

public class AbstractOldDataCard extends AbstractDynamicCard{

    public static final String IMG = makeCardPath("OldData.png");

    public AbstractOldDataCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }



    public void addEffect(OldDataRelic od){
    }


}
