package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.powers.TotemPower;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.util.TextureLoader;

import java.util.HashSet;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class PetFoodRelic extends CustomRelic implements CreatureValueRelic {

    public static final String ID = LeshyMod.makeID(PetFoodRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("pet_food_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("pet_food_relic.png"));


    public static final int BUFF = 1;

    public PetFoodRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);

    }



    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public int giveAttack(AbstractCreatureCard c){
        for(AbstractPower ap : AbstractDungeon.player.powers){
            if(ap instanceof TotemPower){

                HashSet<AbstractCreatureCard.CreatureTribe> tribes = ((TotemPower) ap).getTribe();
                if(tribes.isEmpty())
                    return 0;

                AbstractCreatureCard.CreatureTribe tribe = c.tribe;

                if(tribe == AbstractCreatureCard.CreatureTribe.NONE && LeshyMod.cawCaw)
                    tribe = AbstractCreatureCard.CreatureTribe.AVIAN;

                if(tribe == AbstractCreatureCard.CreatureTribe.ANT)
                    tribe = AbstractCreatureCard.CreatureTribe.INSECT;

                if(!(tribe == AbstractCreatureCard.CreatureTribe.NONE) && (tribe == AbstractCreatureCard.CreatureTribe.AMALGAM || tribes.contains(tribe)))
                    return BUFF;

            }
        }

        return 0;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c){
        for(AbstractPower ap : AbstractDungeon.player.powers){
            if(ap instanceof TotemPower){

                HashSet<AbstractCreatureCard.CreatureTribe> tribes = ((TotemPower) ap).getTribe();
                if(tribes.isEmpty())
                    return 0;

                AbstractCreatureCard.CreatureTribe tribe = c.tribe;

                if(tribe == AbstractCreatureCard.CreatureTribe.NONE && LeshyMod.cawCaw)
                    tribe = AbstractCreatureCard.CreatureTribe.AVIAN;

                if(tribe == AbstractCreatureCard.CreatureTribe.ANT)
                    tribe = AbstractCreatureCard.CreatureTribe.INSECT;

                if(!(tribe == AbstractCreatureCard.CreatureTribe.NONE) && (tribe == AbstractCreatureCard.CreatureTribe.AMALGAM || tribes.contains(tribe)))
                    return BUFF;

            }
        }

        return 0;
    }
}
