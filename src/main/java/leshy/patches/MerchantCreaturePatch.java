package leshy.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.CtBehavior;
import leshy.LeshyMod;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.characters.Leshy;

import java.util.ArrayList;


public class MerchantCreaturePatch {

    @SpirePatch(clz = Merchant.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, int.class})
    public static class ShopCreatureCards{

        @SpireInsertPatch(locator = MerchantCreaturePatch.Locator.class)
        public static void FourCreatures(Merchant __instance, float x, float y, int newShopScreen) {
            if(AbstractDungeon.player instanceof Leshy){
                ArrayList<AbstractCard> cards1 = new ArrayList<>();

                for(int i=0; i<4; i++){
                    AbstractCard c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), Leshy.Enums.CREATURE, true).makeCopy();
                    while (true) {

                        if(c instanceof AbstractCreatureCard && !hasCard(c, cards1) && AbstractDungeon.cardRng.randomBoolean(((AbstractCreatureCard) c).spawnRate)){
                            cards1.add(c);
                            break;
                        }else{
                            c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), Leshy.Enums.CREATURE, true).makeCopy();
                        }

                    }
                }

                AbstractCard c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.POWER, true).makeCopy();
                while (c.color == AbstractCard.CardColor.COLORLESS) {
                    c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.POWER, true).makeCopy();
                }
                cards1.add(c);

                ReflectionHacks.setPrivate(__instance, Merchant.class, "cards1", cards1);
                LeshyMod.logger.info("" + cards1.toString());
            }
        }

    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ShopScreen.class, "init");
            return LineFinder.findInOrder(ctBehavior, methodCallMatcher);
        }
    }

    public static boolean hasCard(AbstractCard card, ArrayList<AbstractCard> list){
        for(AbstractCard c : list){
            if(c.cardID.equals(card.cardID))
                return true;
        }
        return false;
    }

}
