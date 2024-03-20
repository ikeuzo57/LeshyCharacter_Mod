package leshy.powers.interfaces;

import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.HashSet;

public interface CreatureSigilsPower {

    HashSet<AbstractCreatureCard.Sigils> giveSigils(AbstractCreatureCard c);

}
