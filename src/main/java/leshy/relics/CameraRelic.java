package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import leshy.LeshyMod;
import leshy.actions.CameraAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.Amalgam;
import leshy.util.TextureLoader;

import java.util.ArrayList;

import static leshy.LeshyMod.*;

public class CameraRelic extends CustomRelic{

    public static final String ID = LeshyMod.makeID(CameraRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("camera_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("camera_relic.png"));

    public AbstractCreatureCard.CreatureCostType cost = null;
    public int extraCost = -1;

    public int baseAttack = -1;
    public int baseHealth = -1;

    public ArrayList<Amalgam> amalgams = new ArrayList<>();

    public CameraRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);

        counter = 0;
    }


    public void nextCreature(AbstractCreatureCard card){

        flash();
        counter++;

        if(cost == null){
            cost = card.costType;
            extraCost = card.extraCost;
        }else if(baseAttack == -1 && baseHealth == -1){
            baseAttack = card.baseAttack;
            baseHealth = card.baseHealth;
        }else{

            Amalgam amalgam = new Amalgam();
            amalgam.costType = cost;
            amalgam.extraCost = extraCost;
            amalgam.attack = amalgam.baseAttack = baseAttack;
            amalgam.health = amalgam.baseHealth = baseHealth;
            amalgam.gained.addAll(card.innate);
            amalgam.gained.addAll(card.gained);
            amalgam.current.addAll(amalgam.gained);
            amalgam.initializeDescription();

            amalgams.add(amalgam);

            reset();
        }

        updateDescription();

    }

    @Override
    public void atTurnStart(){

        if(!amalgams.isEmpty()){
            for(Amalgam c : amalgams)
                addToBot(new CameraAction(c));
            amalgams.clear();
        }

    }

    public void updateDescription(){

        description = DESCRIPTIONS[0];
        if(cost != null){
            if(cost == AbstractCreatureCard.CreatureCostType.BLOOD)
                description += " Cost : Blood ";
            else if (cost == AbstractCreatureCard.CreatureCostType.BONE)
                description += " Cost : Bone ";
            else
                description += " Cost : ";
            description += extraCost;
        }
        if(baseAttack >= 0 && baseHealth >= 0)
            description += " Attack : " + baseAttack + " Health : " + baseHealth;

        tips.clear();
        tips.add(new PowerTip(name, description));

    }

    @Override
    public void atBattleStart() {
        reset();
    }

    public void reset(){
        counter = 0;
        cost = null;
        extraCost = -1;
        baseAttack = -1;
        baseHealth = -1;
    }


    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }
}
