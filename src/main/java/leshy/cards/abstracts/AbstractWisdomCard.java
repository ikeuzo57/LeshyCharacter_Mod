package leshy.cards.abstracts;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AbstractWisdomCard extends AbstractDynamicCard{

    public AbstractCreatureCard.Sigils wisdom = null;


    public AbstractWisdomCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }



    public void applySigil(AbstractCreatureCard c){
        if(wisdom != null){
            c.gained.add(wisdom);
            c.current.add(wisdom);
        }
    }


}
