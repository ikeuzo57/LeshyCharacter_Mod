package leshy.powers;

import basemod.interfaces.CloneablePowerInterface;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.powers.interfaces.CreatureSigilsPower;
import leshy.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static leshy.LeshyMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class TotemPower extends AbstractPower implements CloneablePowerInterface, CreatureSigilsPower {
    public AbstractCreature source;

    public static final String POWER_ID = LeshyMod.makeID(TotemPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("totem_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("totem_power32.png"));

    private final HashSet<AbstractCreatureCard.CreatureTribe> tribes = new HashSet<>();

    public int maxBases = 1;
    private final ArrayList<AbstractCreatureCard.Sigils> orderedSigils = new ArrayList<>();
    private final HashSet<AbstractCreatureCard.Sigils> sigils = new HashSet<>();


    public TotemPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);



        updateDescription();
    }

    @Override
    public void updateDescription() {

        description = "";

        String tribeString = "";
        if(tribes.isEmpty()){
            tribeString = "___ ";
        }else{

            ArrayList<String> tribeStrings = new ArrayList<>();
            for(AbstractCreatureCard.CreatureTribe tribe : tribes){

                switch (tribe){
                    case CANINE:
                        tribeStrings.add("Canines");
                        break;
                    case AVIAN:
                        tribeStrings.add("Avians");
                        break;
                    case HOOVED:
                        tribeStrings.add("Hooved");
                        break;
                    case ANT:
                    case INSECT:
                        tribeStrings.add("Insects");
                        break;
                    case REPTILE:
                        tribeStrings.add("Reptiles");
                        break;
                    case SQUIRREL:
                        tribeStrings.add("Squirrels");
                        break;
                    case AMALGAM:
                        tribeStrings.add("Amalgams");
                        break;
                }
            }

            Collections.sort(tribeStrings);
            if(tribeStrings.isEmpty()){
                tribeString = "___ ";
            }else{
                tribeString = tribeStrings.get(0);
                for(int i=1; i<tribeStrings.size()-1; i++)
                    tribeString += ", " + tribeStrings.get(i);
                if(tribeStrings.size() >= 2){
                    if(tribeStrings.size() > 2)
                        tribeString += ",";
                    tribeString += " and " + tribeStrings.get(tribeStrings.size()-1);
                }
                tribeString += " ";
            }

        }


        description += tribeString + "gain ";

        ArrayList<String> keywords = new ArrayList<>();
        for(AbstractCreatureCard.Sigils s : sigils)
            keywords.add(AbstractCreatureCard.sigilStrings.get(s));

        if(keywords.isEmpty()){
            description += "___.";
        }else{
            description += keywords.get(0);
            for(int i=1; i<keywords.size()-1; i++){
                description += ", " + keywords.get(i);
            }
            if(keywords.size() >= 2){
                if(keywords.size() > 2){
                    description += ",";
                }
                description += " and " + keywords.get(keywords.size()-1);
            }
            description += ".";
        }

    }

    public void setTribe(AbstractCreatureCard.CreatureTribe set){
        tribes.add(set);
    }

    public HashSet<AbstractCreatureCard.CreatureTribe> getTribe(){
        return tribes;
    }


    public void addSigil(AbstractCreatureCard.Sigils s) {
        if(!sigils.contains(s)){
            sigils.add(s);
            orderedSigils.add(s);

            if (orderedSigils.size() > maxBases){
                sigils.remove(orderedSigils.remove(0));
            }
        }
    }

    @Override
    public HashSet<AbstractCreatureCard.Sigils> giveSigils(AbstractCreatureCard c) {
        if(tribes.isEmpty())
            return new HashSet<>();

        AbstractCreatureCard.CreatureTribe tribe = c.tribe;

        if(tribe == AbstractCreatureCard.CreatureTribe.NONE && LeshyMod.cawCaw)
            tribe = AbstractCreatureCard.CreatureTribe.AVIAN;

        if(tribe == AbstractCreatureCard.CreatureTribe.ANT)
            tribe = AbstractCreatureCard.CreatureTribe.INSECT;

        if(!(tribe == AbstractCreatureCard.CreatureTribe.NONE) && (tribe == AbstractCreatureCard.CreatureTribe.AMALGAM || tribes.contains(tribe)))
            return sigils;


        return new HashSet<>();
    }

    @Override
    public AbstractPower makeCopy() {
        return new TotemPower(owner);
    }

}
