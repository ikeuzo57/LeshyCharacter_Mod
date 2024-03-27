package leshy.cards.abstracts;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import leshy.LeshyMod;
import leshy.actions.*;
import leshy.cards.*;
import leshy.helpers.CreatureSavable;
import leshy.orbs.CreatureOrb;
import leshy.powers.AlphaPower;
import leshy.powers.BonePower;
import leshy.powers.BrokenEggPower;
import leshy.powers.StinkyPower;
import leshy.powers.interfaces.CreatureSigilsPower;
import leshy.powers.interfaces.CreatureValuePower;
import leshy.relics.*;
import leshy.relics.interfaces.CreatureSigilRelic;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.relics.interfaces.OnSummonRelic;

import java.lang.reflect.Type;
import java.util.*;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractCreatureCard extends AbstractDynamicCard implements OnObtainCard, CustomSavable<CreatureSavable>, SpawnModificationCard, BranchingUpgradesCard, StartupCard {

    String id;

    public enum CreatureCostType {
        BLOOD, BONE, NONE
    }

    public enum CreatureTribe {
        NONE, CANINE, AVIAN, INSECT, ANT, REPTILE, HOOVED, AMALGAM, SQUIRREL
    }

    /*
    When adding new Sigils, update:
    makeSigilMap()
    makeKeywordMap()
    new keyword
    new Wisdom card
    WisdomSigilAction
    */
    public enum Sigils {
        AIRBORNE, AMORPHOUS, ANT_SPAWNER, ARMORED, BEES_WITHIN, BIFURCATED, BONE_DIGGER, BONE_KING, BROOD_PARASITE, DAM_BUILDER, DOUBLE_STRIKE, FECUNDITY, FLEDGLING, GUARDIAN, HOARDER, LEADER, LOOSE_TAIL, MANY_LIVES, MIGHTY_LEAP, RABBIT_HOLE, RAMPAGER, SHARP_QUILLS, STINKY, TOUCH_OF_DEATH,
        TRIFURCATED, TRINKET_BEARER, UNKILLABLE, WATERBORNE, WORTHY_SACRIFICE
    }

    public static final HashMap<Sigils, String> sigilStrings = makeSigilMap();
    public static final HashMap<Sigils, String> keywordStrings = makeKeywordMap();

    public static HashMap<Sigils, String> makeSigilMap(){
        HashMap<Sigils, String> map = new HashMap<>();
        map.put(Sigils.AIRBORNE, "Airborne");
        map.put(Sigils.AMORPHOUS, "Amorphous");
        map.put(Sigils.ANT_SPAWNER, "Ant Spawner");
        map.put(Sigils.ARMORED, "Armored");
        map.put(Sigils.BEES_WITHIN, "Bees Within");
        map.put(Sigils.BIFURCATED, "Bifurcated");
        map.put(Sigils.BONE_KING, "Bone King");
        map.put(Sigils.BONE_DIGGER, "Bone Digger");
        map.put(Sigils.BROOD_PARASITE, "Brood Parasite");
        map.put(Sigils.DAM_BUILDER, "Dam Builder");
        map.put(Sigils.DOUBLE_STRIKE, "Double Strike");
        map.put(Sigils.FECUNDITY, "Fecundity");
        map.put(Sigils.FLEDGLING, "Fledgling");
        map.put(Sigils.GUARDIAN, "Guardian");
        map.put(Sigils.HOARDER, "Hoarder");
        map.put(Sigils.LEADER, "Leader");
        map.put(Sigils.LOOSE_TAIL, "Loose Tail");
        map.put(Sigils.MANY_LIVES, "Many Lives");
        map.put(Sigils.MIGHTY_LEAP, "Mighty Leap");
        map.put(Sigils.RABBIT_HOLE, "Rabbit Hole");
        map.put(Sigils.RAMPAGER, "Rampager");
        map.put(Sigils.SHARP_QUILLS, "Sharp Quills");
        map.put(Sigils.STINKY, "Stinky");
        map.put(Sigils.TOUCH_OF_DEATH, "Touch of Death");
        map.put(Sigils.TRIFURCATED, "Trifurcated");
        map.put(Sigils.TRINKET_BEARER, "Trinket Bearer");
        map.put(Sigils.UNKILLABLE, "Unkillable");
        map.put(Sigils.WATERBORNE, "Waterborne");
        map.put(Sigils.WORTHY_SACRIFICE, "Worthy Sacrifice");
        return map;
    }

    public static HashMap<Sigils, String> makeKeywordMap(){
        HashMap<Sigils, String> map = new HashMap<>();
        map.put(Sigils.AIRBORNE, "leshy:Airborne");
        map.put(Sigils.AMORPHOUS, "leshy:Amorphous");
        map.put(Sigils.ANT_SPAWNER, "leshy:Ant_Spawner");
        map.put(Sigils.ARMORED, "leshy:Armored");
        map.put(Sigils.BEES_WITHIN, "leshy:Bees_Within");
        map.put(Sigils.BIFURCATED, "leshy:Bifurcated");
        map.put(Sigils.BONE_KING, "leshy:Bone_King");
        map.put(Sigils.BONE_DIGGER, "leshy:Bone_Digger");
        map.put(Sigils.BROOD_PARASITE, "leshy:Brood_Parasite");
        map.put(Sigils.DAM_BUILDER, "leshy:Dam_Builder");
        map.put(Sigils.DOUBLE_STRIKE, "leshy:Double_Strike");
        map.put(Sigils.FECUNDITY, "leshy:Fecundity");
        map.put(Sigils.FLEDGLING, "leshy:Fledgling");
        map.put(Sigils.GUARDIAN, "leshy:Guardian");
        map.put(Sigils.HOARDER, "leshy:Hoarder");
        map.put(Sigils.LEADER, "leshy:Leader");
        map.put(Sigils.LOOSE_TAIL, "leshy:Loose_Tail");
        map.put(Sigils.MANY_LIVES, "leshy:Many_Lives");
        map.put(Sigils.MIGHTY_LEAP, "leshy:Mighty_Leap");
        map.put(Sigils.RABBIT_HOLE, "leshy:Rabbit_Hole");
        map.put(Sigils.RAMPAGER, "leshy:Rampager");
        map.put(Sigils.SHARP_QUILLS, "leshy:Sharp_Quills");
        map.put(Sigils.STINKY, "leshy:Stinky");
        map.put(Sigils.TOUCH_OF_DEATH, "leshy:Touch_of_Death");
        map.put(Sigils.TRIFURCATED, "leshy:Trifurcated");
        map.put(Sigils.TRINKET_BEARER, "leshy:Trinket_Bearer");
        map.put(Sigils.UNKILLABLE, "leshy:Unkillable");
        map.put(Sigils.WATERBORNE, "leshy:Waterborne");
        map.put(Sigils.WORTHY_SACRIFICE, "leshy:Worthy_Sacrifice");
        return map;
    }

    public HashSet<Sigils> innate = new HashSet<>();
    public HashSet<Sigils> gained = new HashSet<>();
    public HashSet<Sigils> current = new HashSet<>();

    public String sigilImgPath = null;
    public boolean setSigil = false;

    public void setSigilImg(){
        if(!setSigil && !gained.isEmpty()){
            setSigil = true;
            this.textureImg = sigilImgPath;
            if(sigilImgPath != null){
                loadCardImage(sigilImgPath);
            }
        }
    }

    public float titleScale = 1F;

    public static int STINKY_AMOUNT = 1;
    public static final int SHARP_QUILLS_AMOUNT = 3;
    public static final int UPGRADE_AMOUNT = 1;
    public static final int STOAT_BUFF = 1;

    public float mycologistReroll = 1F;
    public float spawnRate = 1F;
    public int mushroomCount = 1;
    public int trueTimesUpgraded = 0;

    public int extraCost = 0;
    public CreatureCostType costType = CreatureCostType.NONE;
    public CreatureTribe tribe;

    public boolean bounce = false;

    public boolean isFrail = false;
    public boolean tempFrail = false;

    public CreatureOrb orb = null;

    public boolean isElder = false;
    public boolean isTransforming = false;
    public boolean transformed = false;

    public int livesLost = 0;

    public boolean applyStinky = false;
    public boolean applySharpQuills = false;
    public boolean applyMightyLeap = false;

    public boolean loseFecundity = false;

    public boolean rampageRight = true;

    public boolean tailTriggered = false;
    public boolean armoredTriggered = false;
    public boolean amorphousTriggered = false;
    public boolean trinketTriggered = false;

    public AlphaPower alpha = null;
    public boolean applyAlpha = false;

    public boolean fleeting = false;
    public boolean baseFleeting = false;

    public boolean bloodless = false;

    public boolean bottledOpossum = false;

    public int attack = 0;
    public int baseAttack = 0;
    public int trueBaseAttack = 0;
    public boolean upgradedAttack = false;
    public boolean isAttackModified = false;

    public int health = 0;
    public int baseHealth = 0;
    public int trueBaseHealth = 0;
    public boolean upgradedHealth = false;
    public boolean isHealthModified = false;


    public AbstractCreatureCard(final String id,
                                final String img,
                                final int cost,
                                final CardType type,
                                final CardColor color,
                                final CardRarity rarity,
                                final CardTarget target) {

        super(id, img, cost, type, color, rarity, target);

        this.id = id;

    }

    @Override
    public void update() {
        super.update();
        setSigilImg();
    }

    @Override
    public void applyPowers() {

        attack = baseAttack;
        health = baseHealth;

        current.clear();
        current.addAll(innate);
        current.addAll(gained);

        fleeting = baseFleeting;

        for(AbstractPower ap : AbstractDungeon.player.powers){
            if(ap instanceof CreatureSigilsPower)
                current.addAll(((CreatureSigilsPower) ap).giveSigils(this));
            if(ap instanceof CreatureValuePower){
                attack += ((CreatureValuePower) ap).giveAttack(this);
                health += ((CreatureValuePower) ap).giveHealth(this);
            }
            if(ap instanceof StrengthPower)
                attack += ap.amount;
            if(ap instanceof DexterityPower)
                health += ap.amount;
        }
        for(AbstractRelic ar : AbstractDungeon.player.relics){
            if(ar instanceof CreatureSigilRelic)
                current.addAll(((CreatureSigilRelic) ar).giveSigils(this));
            if(ar instanceof CreatureValueRelic){
                attack += ((CreatureValueRelic) ar).giveAttack(this);
                health += ((CreatureValueRelic) ar).giveHealth(this);
            }
        }

        double antMultiplier = 1;
        if(this.tribe == CreatureTribe.ANT){
            for(AbstractOrb o : AbstractDungeon.player.orbs){
                if(o instanceof CreatureOrb && ((CreatureOrb) o).creatureCard.uuid != this.uuid){
                    if(((CreatureOrb) o).creatureCard.tribe == CreatureTribe.ANT || ((CreatureOrb) o).creatureCard.tribe == CreatureTribe.AMALGAM){
                        antMultiplier += 0.5;
                    }
                }
            }
        }

        double elderMultiplier = 1;
        if(isElder){
            elderMultiplier = 1.2;
            current.remove(Sigils.FLEDGLING);
        }

        double weak = 1;
        for(AbstractPower ap : AbstractDungeon.player.powers){
            if(ap instanceof WeakPower){
                weak = 0.75;
                break;
            }
        }

        double frail = 1;
        tempFrail = false;
        if(isFrail){
            frail = 0.75;
        }else if(this.orb == null || AbstractDungeon.player.hand.contains(this)){
            for(AbstractPower ap : AbstractDungeon.player.powers){
                if(ap instanceof FrailPower){
                    frail = 0.75;
                    tempFrail = true;
                    break;
                }
            }
        }


        attack = (int) (antMultiplier * elderMultiplier * specialAttackMultiplier() * weak * attack);
        health = (int) (elderMultiplier * specialHealthMultiplier() * frail * health);
        if(attack < 0)
            attack = 0;
        if(health < 1)
            health = 1;

        super.applyPowers();

        isAttackModified = (attack != baseAttack);
        isHealthModified = (health != baseHealth);

        initializeDescription();

    }

    public int getStoatBuff() {
        int stoatBuff = STOAT_BUFF * current.size();
        if (current.contains(Sigils.FLEDGLING) && isElder)
            stoatBuff -= STOAT_BUFF;
        if (current.contains(Sigils.FECUNDITY) && loseFecundity)
            stoatBuff -= STOAT_BUFF;
        if (current.contains(Sigils.ARMORED) && armoredTriggered)
            stoatBuff -= STOAT_BUFF;
        if (current.contains(Sigils.LOOSE_TAIL) && tailTriggered)
            stoatBuff -= STOAT_BUFF;
        if (current.contains(Sigils.AMORPHOUS) && amorphousTriggered)
            stoatBuff -= STOAT_BUFF;
        if(current.contains(Sigils.TRINKET_BEARER) && trinketTriggered)
            stoatBuff -= STOAT_BUFF;
        return stoatBuff;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        attack = baseAttack;
        health = baseHealth;

        current.clear();
        current.addAll(innate);
        current.addAll(gained);

        fleeting = baseFleeting;

        for(AbstractPower ap : AbstractDungeon.player.powers){
            if(ap instanceof CreatureSigilsPower)
                current.addAll(((CreatureSigilsPower) ap).giveSigils(this));
            if(ap instanceof CreatureValuePower){
                attack += ((CreatureValuePower) ap).giveAttack(this);
                health += ((CreatureValuePower) ap).giveHealth(this);
            }
            if(ap instanceof StrengthPower)
                attack += ap.amount;
            if(ap instanceof DexterityPower)
                health += ap.amount;
        }
        for(AbstractRelic ar : AbstractDungeon.player.relics){
            if(ar instanceof CreatureSigilRelic)
                current.addAll(((CreatureSigilRelic) ar).giveSigils(this));
            if(ar instanceof CreatureValueRelic){
                attack += ((CreatureValueRelic) ar).giveAttack(this);
                health += ((CreatureValueRelic) ar).giveHealth(this);
            }
        }

        double antMultiplier = 1;
        if(this.tribe == CreatureTribe.ANT){
            for(AbstractOrb o : AbstractDungeon.player.orbs){
                if(o instanceof CreatureOrb && ((CreatureOrb) o).creatureCard.uuid != this.uuid){
                    if(((CreatureOrb) o).creatureCard.tribe == CreatureTribe.ANT || ((CreatureOrb) o).creatureCard.tribe == CreatureTribe.AMALGAM){
                        antMultiplier += 0.5;
                    }
                }
            }
        }

        double elderMultiplier = 1;
        if(isElder){
            elderMultiplier = 1.2;
            current.remove(Sigils.FLEDGLING);
        }

        double weak = 1;
        for(AbstractPower ap : AbstractDungeon.player.powers){
            if(ap instanceof WeakPower){
                weak = 0.75;
                break;
            }
        }

        double frail = 1;
        tempFrail = false;
        if(isFrail){
            frail = 0.75;
        }else if(this.orb == null || AbstractDungeon.player.hand.contains(this)){
            for(AbstractPower ap : AbstractDungeon.player.powers){
                if(ap instanceof FrailPower){
                    frail = 0.75;
                    tempFrail = true;
                    break;
                }
            }
        }


        attack = (int) (antMultiplier * elderMultiplier * specialAttackMultiplier() * weak * attack);
        health = (int) (elderMultiplier * specialHealthMultiplier() * frail * health);
        if(attack < 0)
            attack = 0;
        if(health < 1)
            health = 1;

        super.calculateCardDamage(mo);

        isAttackModified = (attack != baseAttack);
        isHealthModified = (health != baseHealth);

        initializeDescription();
    }

    public double specialAttackMultiplier(){
        return 1.0;
    }

    public double specialHealthMultiplier(){
        return 1.0;
    }

    @Override
    public void initializeDescription() {
        this.rawDescription = extraText();

        if(tribe == CreatureTribe.ANT)
            this.rawDescription = "leshy:Ant";

        String keywords = "";

        if(isEthereal)
            keywords += "Ethereal ";
        if(selfRetain)
            keywords += "Retain ";
        if(isFrail || tempFrail)
            keywords += "Frail ";
        if(bloodless && !current.contains(Sigils.WORTHY_SACRIFICE))
            keywords += "leshy:Bloodless ";
        if(fleeting && !current.contains(Sigils.UNKILLABLE))
            keywords += "leshy:Transient ";

        if(!keywords.isEmpty()){
            keywords = keywords.substring(0, keywords.length() - 1);
            if(!this.rawDescription.isEmpty())
                this.rawDescription += " NL ";
            this.rawDescription += keywords;
        }


        String sigils = getSigils();
        if(!sigils.isEmpty()){
            if(!this.rawDescription.isEmpty())
                this.rawDescription += " NL ";
            this.rawDescription += "Sigils : " + sigils;
        }



        super.initializeDescription();
    }

    public String getSigils(){
        String keywords = "";

        if(current != null){

            ArrayList<Sigils> toSort = new ArrayList<>(current);
            Collections.sort(toSort);

            for (Sigils s : toSort){

                if (s == Sigils.ARMORED && armoredTriggered)
                    continue;
                if (s == Sigils.FECUNDITY && loseFecundity)
                    continue;
                if (s == Sigils.FLEDGLING && isElder)
                    continue;
                if (s == Sigils.LOOSE_TAIL && tailTriggered)
                    continue;
                if(s == Sigils.AMORPHOUS && amorphousTriggered)
                    continue;
                if(s == Sigils.TRINKET_BEARER && trinketTriggered)
                    continue;

                keywords += keywordStrings.get(s) + " ";

                if (s == Sigils.MANY_LIVES){
                    keywords += (9 - this.livesLost) + " ";
                } else if (s == Sigils.RAMPAGER){
                    if (rampageRight)
                        keywords += "Right ";
                    else
                        keywords += "Left ";
                }

            }
        }

        if(!keywords.isEmpty())
            keywords = keywords.substring(0, keywords.length()-1);
        return keywords;
    }





    public static String tribeText(CreatureTribe tribe){
        switch (tribe){
            case CANINE:
                return "Canine";
            case AVIAN:
                return "Avian";
            case INSECT:
                return "Insect";
            case ANT:
                return "Insect leshy:Ant";
            case REPTILE:
                return "Reptile";
            case HOOVED:
                return "Hooved";
            case AMALGAM:
                return "leshy:Amalgam";
            case SQUIRREL:
                return "Squirrel";
            default:
                return "something went wrong";
        }
    }

    public String extraText(){
        return "";
    }

    public void onPassive(){

        if(this.attack > 0 || (LeshyMod.sapling && this.tribe == CreatureTribe.NONE))
            addToBot(new CreatureAttackAction(this));

        if(current.contains(Sigils.STINKY) && !applyStinky){
            applyStinky = true;
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                if(m.currentHealth > 0 && !m.isDeadOrEscaped()){
                    addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new StinkyPower(m, AbstractDungeon.player, STINKY_AMOUNT), STINKY_AMOUNT));
                }
            }
        }
        if(current.contains(Sigils.SHARP_QUILLS) && !applySharpQuills){
            applySharpQuills = true;
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, SHARP_QUILLS_AMOUNT)));
        }
        if(current.contains(Sigils.MIGHTY_LEAP) && !applyMightyLeap){
            applyMightyLeap = true;
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, 1), 1));
        }
        if(current.contains(Sigils.LEADER) && !applyAlpha){
            applyAlpha = true;
            alpha = new AlphaPower(this);
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, alpha));
        }

        if(current.contains(Sigils.RAMPAGER)){
            if(this instanceof WildBull && LeshyMod.bullfighter)
                addToBot(new RampagerAction(this.orb));
            addToBot(new RampagerAction(this.orb));
        }

        if(current.contains(Sigils.BONE_DIGGER))
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BonePower(AbstractDungeon.player, this, 1)));

    }

    public void onSummon(){

        for(AbstractRelic r : AbstractDungeon.player.relics) {
            if(r instanceof OnSummonRelic)
                ((OnSummonRelic) r).onSummon(this);
        }

        bounce = false;
        livesLost = 0;

        if(current.contains(Sigils.FECUNDITY) && !loseFecundity){

            AbstractCreatureCard temp = (AbstractCreatureCard) this.makeStatEquivalentCopy();

            temp.baseFleeting = true;
            temp.loseFecundity = true;

            addToBot(new MakeTempCardInHandAction(temp));

        }
        if(current.contains(Sigils.STINKY) && !applyStinky){
            applyStinky = true;
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                if(m.currentHealth > 0 && !m.isDeadOrEscaped()){
                    int amount = STINKY_AMOUNT;
                    for(AbstractRelic r: AbstractDungeon.player.relics)
                        if(r instanceof PileOfMeatRelic){
                            amount++;
                            break;
                        }
                    addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new StinkyPower(m, AbstractDungeon.player, amount), amount));
                }
            }
        }
        if(current.contains(Sigils.SHARP_QUILLS) && !applySharpQuills){
            applySharpQuills = true;
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, SHARP_QUILLS_AMOUNT)));
        }
        if(current.contains(Sigils.HOARDER)){
            //addToBot(new HoarderAction());
            addToBot(new SeekAction(1));
        }
        if(current.contains(Sigils.ANT_SPAWNER)){
            WorkerAnt ant = new WorkerAnt();

            if(this.innate.contains(Sigils.ANT_SPAWNER))
                ant.transform(this);

            ant.baseFleeting = true;
            ant.applyPowers();
            addToBot(new MakeTempCardInHandAction(ant));
        }
        if(current.contains(Sigils.RABBIT_HOLE)){
            Rabbit rabbit = new Rabbit();

            if(this.innate.contains(Sigils.RABBIT_HOLE))
                rabbit.transform(this);

            rabbit.applyPowers();
            addToBot(new MakeTempCardInHandAction(rabbit));
        }
        if(current.contains(Sigils.BROOD_PARASITE)){
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
                if(m.currentHealth > 0 && !m.isDeadOrEscaped())
                    addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new BrokenEggPower(m, AbstractDungeon.player, 1), 1));
        }
        if(current.contains(Sigils.MIGHTY_LEAP)){
            applyMightyLeap = true;
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, 1), 1));
        }
        if(current.contains(Sigils.LEADER)){
            alpha = new AlphaPower(this);
            applyAlpha = true;
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, alpha));
        }
        if(current.contains(Sigils.DAM_BUILDER)){
            Dam dam1 = new Dam();
            Dam dam2 = new Dam();
            if(this.innate.contains(Sigils.DAM_BUILDER)){
                dam1.transform(this);
                dam2.transform(this);
            }
            addToBot(new SummonCreatureAction(new CreatureOrb(dam1), AbstractDungeon.player.orbs.indexOf(this.orb)));
            addToBot(new SummonCreatureAction(new CreatureOrb(dam2), AbstractDungeon.player.orbs.indexOf(this.orb)+2));
        }
        if(current.contains(Sigils.TRINKET_BEARER) && !trinketTriggered){
            trinketTriggered = true;
            addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
        }
        if(!transformed) {
            for (AbstractPower ap : AbstractDungeon.player.powers){
                if (ap instanceof FrailPower){
                    isFrail = true;
                    break;
                }
            }
        }
        transformed = false;

    }

    public void onSacrifice(){

        LeshyMod.logger.info("Sacrifice : " + this.name);

        if(orb == null)
            return;

        AbstractPlayer p = AbstractDungeon.player;

        boolean diedToDamage = orb.dead;
        orb = null;
        this.tailTriggered = false;
        this.armoredTriggered = false;
        this.isFrail = false;

        if(alpha != null){
            addToBot(new RemoveSpecificPowerAction(p, p, alpha));
            alpha = null;
        }

        if(applyMightyLeap){
            applyMightyLeap = false;
            int numMightyLeap = 0;
            for(AbstractOrb o : AbstractDungeon.player.orbs)
                if(o instanceof CreatureOrb && ((CreatureOrb) o).creatureCard.current.contains(Sigils.MIGHTY_LEAP))
                    numMightyLeap++;
            for(AbstractPower ap : AbstractDungeon.player.powers){
                if(ap instanceof ArtifactPower){
                    if (ap.amount >= numMightyLeap)
                        addToTop(new ReducePowerAction(p, p, "Artifact", 1));
                    break;
                }
            }
        }

        if(applyStinky){
            applyStinky = false;

            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                if(m.currentHealth > 0 && !m.isDeadOrEscaped()){
                    for(AbstractPower ap : m.powers){
                        if(ap instanceof StinkyPower){
                            int amount = STINKY_AMOUNT;
                            for(AbstractRelic r: AbstractDungeon.player.relics)
                                if(r instanceof PileOfMeatRelic){
                                    amount++;
                                    break;
                                }
                            addToBot(new ReducePowerAction(m, AbstractDungeon.player, ap, amount));
                            break;
                        }
                    }
                }
            }
        }
        if(applySharpQuills){
            applySharpQuills = false;
            for(AbstractPower ap : p.powers){
                if(ap instanceof ThornsPower){
                    addToBot(new ReducePowerAction(p, p, ap, SHARP_QUILLS_AMOUNT));
                    break;
                }
            }
        }

        if(this.isTransforming)
            return;


        if(bounce){
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            temp.addToTop(this);
            if(AbstractDungeon.player.hand.size() < 10) {
                temp.moveToHand(this);
            }else{
                temp.moveToDiscardPile(this);
            }
            return;
        }


        if(!current.contains(Sigils.UNKILLABLE)) {
            if(current.contains(Sigils.BONE_KING))
                addToTop(new ApplyPowerAction(p, p, new BonePower(p, this, 4)));
            else
                addToTop(new ApplyPowerAction(p, p, new BonePower(p, this, 1)));
        }


        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        temp.addToTop(this);
        if(current.contains(Sigils.UNKILLABLE) && !diedToDamage){
            if(p.hand.size() < 10) {
                temp.moveToHand(this);
            }else{
                AbstractDungeon.player.createHandIsFullDialog();
                temp.moveToDiscardPile(this);
            }
        }else if(fleeting && !current.contains(Sigils.UNKILLABLE)){
            addToTop(new ExhaustSpecificCardAction(this, temp));
        }else{
            temp.moveToDiscardPile(this);
        }

        initializeDescription();

        for(AbstractRelic r : AbstractDungeon.player.relics){
            if(r instanceof ChimeraStatueRelic)
                ((ChimeraStatueRelic) r).updateTribeCount();
            if(r instanceof CameraRelic && diedToDamage)
                ((CameraRelic) r).nextCreature(this);
            if(r instanceof BloodstoneRelic && diedToDamage && (this.tribe == CreatureTribe.SQUIRREL || this.tribe == CreatureTribe.AMALGAM))
                ((BloodstoneRelic) r).squirrelDeath();
        }

    }

    public void onStartOfTurn() {
        if(current.contains(Sigils.FLEDGLING) && !innate.contains(Sigils.FLEDGLING) && !isElder){
            isElder = true;
            updateName();
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        applyPowers();
    }

    @Override
    public boolean atBattleStartPreDraw() {

        if(this.tribe == CreatureTribe.NONE)
            for(AbstractRelic r : AbstractDungeon.player.relics)
                if(r instanceof PaintbrushRelic){
                    this.gained.add(Sigils.AMORPHOUS);
                    this.current.add(Sigils.AMORPHOUS);
                }

        if(this.current.contains(Sigils.AMORPHOUS) && !this.amorphousTriggered){
            this.amorphousTriggered = true;
            BloodCreatureAction.addRandomSigils(this, 1);
        }

        return false;
    }

    public void onDamage(){

        if(current.contains(Sigils.BEES_WITHIN))
            addToBot(new BeesWithinAction(this));

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        if(costType == CreatureCostType.BLOOD && extraCost > 0){

            if(LeshyMod.blood)
                return super.canUse(p, m);

            int totalBloodAvailable = 0;
            for(AbstractOrb o : p.orbs){
                if(o instanceof CreatureOrb && !(((CreatureOrb) o).creatureCard.bloodless && !((CreatureOrb) o).creatureCard.current.contains(AbstractCreatureCard.Sigils.WORTHY_SACRIFICE)) && !(((CreatureOrb) o).creatureCard instanceof Starvation)){
                    if(((CreatureOrb) o).creatureCard.current.contains(Sigils.WORTHY_SACRIFICE)){
                        totalBloodAvailable += 3;
                    }else{
                        totalBloodAvailable ++;
                    }
                }
            }
            if(totalBloodAvailable >= extraCost){
                return super.canUse(p, m);
            }else{
                return false;
            }

        }else if(costType == CreatureCostType.BONE){

            int bonesAvailable = 0;
            for(AbstractPower ap : p.powers){
                if(ap instanceof BonePower){
                    bonesAvailable = ap.amount;
                    break;
                }
            }
            if(bonesAvailable >= extraCost){

                boolean freeSpace = false;
                for(AbstractOrb o : p.orbs){
                    if(o instanceof EmptyOrbSlot){
                        freeSpace = true;
                        break;
                    }
                }
                if(freeSpace){
                    return super.canUse(p, m);
                }else{
                    return false;
                }

            }else{
                return false;
            }

        } else if(costType == CreatureCostType.NONE){
            boolean freeSpace = false;
            for(AbstractOrb o : p.orbs){
                if(o instanceof EmptyOrbSlot){
                    freeSpace = true;
                    break;
                }
            }
            if(freeSpace){
                return super.canUse(p, m);
            }else{
                return false;
            }
        }
        return super.canUse(p, m);
    }

    public void sigilOption(AbstractCreatureCard card){
        sigilOption(card, true);
    }

    public void sigilOption(AbstractCreatureCard card, boolean remove){

        this.gained.addAll(card.innate);
        this.current.addAll(card.innate);

        this.gained.removeAll(this.innate);

        initializeDescription();

        if(remove)
            AbstractDungeon.player.masterDeck.removeCard(card);

    }

    public void mycologistOption(AbstractCreatureCard card, float statRatio){

        this.isStatic = (this.isStatic || card.isStatic);

        this.baseAttack += (int)(card.baseAttack * statRatio);
        this.baseHealth += (int)(card.baseHealth * statRatio);
        this.attack = this.baseAttack;
        this.health = this.baseHealth;

        this.gained.addAll(card.gained);
        this.current.addAll(card.gained);

        this.mushroomCount += card.mushroomCount;
        this.trueTimesUpgraded += card.trueTimesUpgraded;
        if(this.trueTimesUpgraded > 0){
            this.upgraded = true;
            this.name = languagePack.getCardStrings(id).NAME + "+" + this.trueTimesUpgraded;
        }

        initializeDescription();

        AbstractDungeon.player.masterDeck.removeCard(card);

    }

    public void transform(AbstractCreatureCard card) {

        this.gained.addAll(card.gained);
        this.current.addAll(card.gained);

        this.loseFecundity = card.loseFecundity;
        this.amorphousTriggered = card.amorphousTriggered;
        this.trinketTriggered = card.trinketTriggered;
        this.isStatic = card.isStatic;

    }

    public boolean canUpgrade(){
        return true;
    }

    public void upgrade() {

        int buff = UPGRADE_AMOUNT;
        if(AbstractDungeon.player != null){
            for(AbstractRelic r : AbstractDungeon.player.relics)
                if(r instanceof DeadSurvivorsRelic){
                    buff *= 2;
                    break;
                }
        }

        this.trueTimesUpgraded++;
        updateName();

        upgradedAttack = false;
        upgradedHealth = false;

        this.timesUpgraded = 0;

        if(upMagicNumber != 0){
            upgradeMagicNumber(upMagicNumber);
        }
        if(upSecondMagicNumber != 0){
            upgradeSecondMagicNumber(upSecondMagicNumber);
        }

        switch (getUpgradeType()){
            case NORMAL_UPGRADE:
                this.baseAttack += (buff*2);
                this.attack = this.baseAttack;
                upgradedAttack = true;
                this.timesUpgraded++;
                break;
            case BRANCH_UPGRADE:
                this.baseHealth += (buff*2);
                this.health = this.baseHealth;
                upgradedHealth = true;
                this.timesUpgraded--;
                break;
            case RANDOM_UPGRADE:
                this.baseAttack += buff;
                this.baseHealth += buff;
                this.attack = this.baseAttack;
                this.health = this.baseHealth;
                upgradedAttack = true;
                upgradedHealth = true;
                break;
        }

        setUpgradeType(UpgradeType.RANDOM_UPGRADE);

        initializeTitle();
        initializeDescription();
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedAttack) {
            attack = baseAttack;
            isAttackModified = true;
        }
        if (upgradedHealth) {
            health = baseHealth;
            isHealthModified = true;
        }
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        return AbstractDungeon.cardRng.randomBoolean(spawnRate);
    }


    @Override
    public CreatureSavable onSave() {

        CreatureSavable save = new CreatureSavable();
        save.baseAttack = this.baseAttack;
        save.baseHealth = this.baseHealth;
        save.mushroomCount = this.mushroomCount;
        save.trueTimesUpgraded = this.trueTimesUpgraded;
        save.gained.addAll(this.gained);
        save.tribe = this.tribe;
        save.magic = this.baseMagicNumber;
        save.secondMagic = this.baseSecondMagicNumber;
        save.bottledOpossum = this.bottledOpossum;
        save.baseFleeting = this.baseFleeting;
        save.costType = this.costType;
        save.extraCost = this.extraCost;
        save.isStatic = this.isStatic;

        return save;

    }

    @Override
    public void onLoad(CreatureSavable save) {

        this.baseAttack = this.attack = save.baseAttack;
        this.baseHealth = this.health = save.baseHealth;
        this.mushroomCount = save.mushroomCount;
        this.trueTimesUpgraded = save.trueTimesUpgraded;
        updateName();
        this.gained.addAll(save.gained);
        this.current.addAll(save.gained);
        this.tribe = save.tribe;
        this.baseMagicNumber = this.magicNumber = save.magic;
        this.baseSecondMagicNumber = this.secondMagicNumber = save.secondMagic;
        this.bottledOpossum = save.bottledOpossum;
        this.fleeting = this.baseFleeting = save.baseFleeting;
        this.costType = save.costType;
        this.extraCost = save.extraCost;
        this.isStatic = save.isStatic;

    }

    @Override
    public Type savedType(){
        return new TypeToken<CreatureSavable>(){}.getType();
    }

    public void updateName(){

        if(isElder)
            this.name = elderName();
        else
            this.name = languagePack.getCardStrings(id).NAME;

        if(this.trueTimesUpgraded > 0){
            this.upgraded = true;
            this.name = this.name + "+" + this.trueTimesUpgraded;
        }
    }

    public String elderName(){
        return "Elder " + languagePack.getCardStrings(id).NAME;
    }

    @Override
    public void onRemoveFromMasterDeck() {
        super.onRemoveFromMasterDeck();
        LeshyMod.updateFullSets();
    }

    @Override
    public void onObtainCard() {
        LeshyMod.updateFullSets();
    }


    @Override
    public TextureAtlas.AtlasRegion getCardBgAtlas() {
        return ImageMaster.CARD_SKILL_BG_SILHOUETTE;
    }


    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCreatureCard c = (AbstractCreatureCard) super.makeStatEquivalentCopy();

        c.isStatic = this.isStatic;

        c.isElder = this.isElder;

        c.costType = this.costType;
        c.extraCost = this.extraCost;

        c.baseAttack = this.baseAttack;
        c.baseHealth = this.baseHealth;
        c.attack = c.baseAttack;
        c.health = c.baseHealth;
        c.mushroomCount = this.mushroomCount;
        c.trueTimesUpgraded = this.trueTimesUpgraded;
        c.updateName();

        c.tribe = this.tribe;

        c.gained.addAll(this.gained);
        c.current.addAll(this.gained);

        c.livesLost = this.livesLost;
        c.loseFecundity = this.loseFecundity;
        c.amorphousTriggered = this.amorphousTriggered;
        c.trinketTriggered = this.trinketTriggered;
        c.fleeting = c.baseFleeting = this.baseFleeting;

        c.isEthereal = this.isEthereal;

        c.bottledOpossum = this.bottledOpossum;

        c.initializeDescription();

        return c;
    }
}


















