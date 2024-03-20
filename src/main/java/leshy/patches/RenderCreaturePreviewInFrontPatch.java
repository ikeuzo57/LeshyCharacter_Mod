package leshy.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import leshy.orbs.CreatureOrb;

@SpirePatch(clz = AbstractPlayer.class, method = "render")
public class RenderCreaturePreviewInFrontPatch {

    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer obj, SpriteBatch sb) {
        for (AbstractOrb o : obj.orbs) {
            if(o instanceof CreatureOrb){
                ((CreatureOrb) o).renderPreview(sb);
            }
        }
    }


}
