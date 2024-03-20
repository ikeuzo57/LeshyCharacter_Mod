package leshy.cards.abstracts;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import leshy.LeshyMod;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractLeshyCard extends CustomCard {

    public int upCost;
    public int upDamage;
    public int upBlock;
    public int upMagicNumber;

    public int secondMagicNumber;
    public int baseSecondMagicNumber;
    public int upSecondMagicNumber;
    public boolean upgradedSecondMagicNumber;
    public boolean isSecondMagicNumberModified;

    public boolean upgradeDescription;

    public boolean isStatic = false;
    public int staticFrame = 0;

    public static final int STATIC_FRAMES = 4;


    public AbstractLeshyCard(final String id,
                             final String name,
                             final String img,
                             final int cost,
                             final String rawDescription,
                             final CardType type,
                             final CardColor color,
                             final CardRarity rarity,
                             final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;

        isSecondMagicNumberModified = false;
        upCost = -1;
        upDamage = 0;
        upBlock = 0;
        upMagicNumber = 0;

        upSecondMagicNumber = 0;

        upgradeDescription = false;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            if(upCost != -1){
                upgradeBaseCost(upCost);
            }
            if(upDamage != 0){
                upgradeDamage(upDamage);
            }
            if(upBlock != 0){
                upgradeBlock(upBlock);
            }
            if(upMagicNumber != 0){
                upgradeMagicNumber(upMagicNumber);
            }
            if(upSecondMagicNumber != 0){
                upgradeSecondMagicNumber(upSecondMagicNumber);
            }
            if(upgradeDescription){
                this.rawDescription = languagePack.getCardStrings(cardID).UPGRADE_DESCRIPTION;
            }
            initializeDescription();
        }
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagicNumber) {
            secondMagicNumber = baseSecondMagicNumber;
            isSecondMagicNumberModified = true;
        }
    }

    public void upgradeSecondMagicNumber(int amount) {
        baseSecondMagicNumber += amount;
        secondMagicNumber += amount;
        upgradedSecondMagicNumber = true;
    }


    public void renderStatic(SpriteBatch sb){
        this.staticFrame = (this.staticFrame + 1) % (AbstractCreatureCard.STATIC_FRAMES*4);
        int frame = this.staticFrame / AbstractCreatureCard.STATIC_FRAMES;

        Color color = Color.WHITE.cpy();
        color.a = this.transparency;
        sb.setColor(color);

        TextureAtlas.AtlasRegion staticFrame;
        switch (frame){
            case 0:
                staticFrame = LeshyMod.getLeshyAtlasRegion("static 1");
                break;
            case 1:
                staticFrame = LeshyMod.getLeshyAtlasRegion("static 2");
                break;
            case 2:
                staticFrame = LeshyMod.getLeshyAtlasRegion("static 3");
                break;
            case 3:
            default:
                staticFrame = LeshyMod.getLeshyAtlasRegion("static 4");
                break;
        }
        sb.draw(staticFrame, this.current_x + staticFrame.offsetX - staticFrame.originalWidth / 2.0F, this.current_y + staticFrame.offsetY - staticFrame.originalHeight / 2.0F, staticFrame.originalWidth / 2.0F - staticFrame.offsetX, staticFrame.originalHeight / 2.0F - staticFrame.offsetY, staticFrame.packedWidth, staticFrame.packedHeight, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle);

    }


    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        if(c instanceof AbstractLeshyCard){
            ((AbstractLeshyCard) c).secondMagicNumber = this.secondMagicNumber;
            ((AbstractLeshyCard) c).baseSecondMagicNumber = this.baseSecondMagicNumber;
        }
        c.initializeDescription();
        return c;
    }
}