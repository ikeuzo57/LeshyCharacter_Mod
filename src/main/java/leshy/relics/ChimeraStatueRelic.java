package leshy.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.orbs.CreatureOrb;
import leshy.relics.interfaces.CreatureValueRelic;
import leshy.relics.interfaces.OnSummonRelic;
import leshy.util.TextureLoader;

import java.util.HashSet;

import static leshy.LeshyMod.makeRelicOutlinePath;
import static leshy.LeshyMod.makeRelicPath;

public class ChimeraStatueRelic extends CustomRelic implements CreatureValueRelic, OnSummonRelic {

    public static final String ID = LeshyMod.makeID(ChimeraStatueRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("chimera_statue_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("chimera_statue_relic.png"));

    private static final int BUFF = 1;

    public ChimeraStatueRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);

        counter = 0;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];

    }

    @Override
    public void atBattleStart() {
        counter = 0;
    }

    @Override
    public void onVictory() {
        counter = 0;
    }

    @Override
    public int giveAttack(AbstractCreatureCard c) {
        return BUFF * counter;
    }

    @Override
    public int giveHealth(AbstractCreatureCard c) {
        return 0;
    }

    public void updateTribeCount(){
        int count = 0;

        if(AbstractDungeon.player != null && AbstractDungeon.player.orbs != null){

            HashSet<AbstractCreatureCard.CreatureTribe> tribes = new HashSet<>();

            for(AbstractOrb o : AbstractDungeon.player.orbs){
                if(o instanceof CreatureOrb){
                    AbstractCreatureCard.CreatureTribe tribe = ((CreatureOrb) o).creatureCard.tribe;
                    if(tribe == AbstractCreatureCard.CreatureTribe.NONE && LeshyMod.cawCaw)
                        tribe = AbstractCreatureCard.CreatureTribe.AVIAN;
                    if(tribe == AbstractCreatureCard.CreatureTribe.ANT)
                        tribe = AbstractCreatureCard.CreatureTribe.INSECT;
                    if(tribe != AbstractCreatureCard.CreatureTribe.NONE)
                        tribes.add(tribe);
                }
            }

            count = tribes.size();

        }

        counter = count;
    }

    @Override
    public void onSummon(AbstractCreatureCard c) {
        updateTribeCount();
    }
}
