package leshy.relics.interfaces;

import leshy.cards.abstracts.AbstractCreatureCard;

public interface OnSacrificeRelic {

    void onSacrifice(AbstractCreatureCard c, boolean diedToDamage);

}
