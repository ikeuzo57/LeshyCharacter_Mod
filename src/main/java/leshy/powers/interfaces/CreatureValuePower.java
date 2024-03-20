package leshy.powers.interfaces;

import leshy.cards.abstracts.AbstractCreatureCard;

public interface CreatureValuePower {
    int giveAttack(AbstractCreatureCard c);

    int giveHealth(AbstractCreatureCard c);
}
