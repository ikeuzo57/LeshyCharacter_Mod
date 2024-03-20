package leshy.relics.interfaces;

import leshy.cards.abstracts.AbstractCreatureCard;

public interface CreatureValueRelic {

    int giveAttack(AbstractCreatureCard c);

    int giveHealth(AbstractCreatureCard c);

}
