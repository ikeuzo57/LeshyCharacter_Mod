package leshy.helpers;

import leshy.cards.abstracts.AbstractCreatureCard;

import java.util.LinkedHashSet;

public class CreatureSavable {

    public int baseAttack;
    public int baseHealth;

    public LinkedHashSet<AbstractCreatureCard.Sigils> gained = new LinkedHashSet<>();

    public AbstractCreatureCard.CreatureTribe tribe;

    public AbstractCreatureCard.CreatureCostType costType;
    public int extraCost;

    public boolean bottledOpossum = false;

    public boolean baseFleeting = false;

    public int mushroomCount;
    public int trueTimesUpgraded;

    public int magic;
    public int secondMagic;

    public boolean isStatic;

}
