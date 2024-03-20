package leshy.relics.interfaces;

import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.HashSet;

public interface CreatureSigilRelic {

    HashSet<AbstractCreatureCard.Sigils> giveSigils(AbstractCreatureCard c);

}
