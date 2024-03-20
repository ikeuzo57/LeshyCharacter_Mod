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

public class DiscriminationRelic extends CustomRelic implements CreatureValueRelic {

    public static final String ID = LeshyMod.makeID(DiscriminationRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("discrimination_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("discrimination_relic.png"));


    public static final int BUFF = -2;

    public DiscriminationRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);

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
                    return BUFF;

                AbstractCreatureCard.CreatureTribe tribe = c.tribe;

                if(tribe == AbstractCreatureCard.CreatureTribe.NONE && LeshyMod.cawCaw)
                    tribe = AbstractCreatureCard.CreatureTribe.AVIAN;

                if(tribe == AbstractCreatureCard.CreatureTribe.ANT)
                    tribe = AbstractCreatureCard.CreatureTribe.INSECT;

                if(!(tribe == AbstractCreatureCard.CreatureTribe.NONE) && (tribe == AbstractCreatureCard.CreatureTribe.AMALGAM || tribes.contains(tribe)))
                    return 0;

            }
        }

        return BUFF;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c){
        for(AbstractPower ap : AbstractDungeon.player.powers){
            if(ap instanceof TotemPower){

                HashSet<AbstractCreatureCard.CreatureTribe> tribes = ((TotemPower) ap).getTribe();
                if(tribes.isEmpty())
                    return BUFF;

                AbstractCreatureCard.CreatureTribe tribe = c.tribe;

                if(tribe == AbstractCreatureCard.CreatureTribe.NONE && LeshyMod.cawCaw)
                    tribe = AbstractCreatureCard.CreatureTribe.AVIAN;

                if(tribe == AbstractCreatureCard.CreatureTribe.ANT)
                    tribe = AbstractCreatureCard.CreatureTribe.INSECT;

                if(!(tribe == AbstractCreatureCard.CreatureTribe.NONE) && (tribe == AbstractCreatureCard.CreatureTribe.AMALGAM || tribes.contains(tribe)))
                    return 0;

            }
        }

        return BUFF;
    }
}
