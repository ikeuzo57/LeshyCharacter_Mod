package leshy.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockImpactLineEffect;
import leshy.LeshyMod;
import leshy.actions.CreatureSacrificeAction;
import leshy.actions.MoveOrbSlotAction;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.cards.TheMoon;
import leshy.relics.BearTrapRelic;
import leshy.relics.OldDataRelic;
import leshy.vfx.AddCardToOrbEffect;

public class CreatureOrb extends AbstractOrb{

    public static final String ORB_ID = LeshyMod.makeID(CreatureOrb.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;

    public AbstractCreatureCard creatureCard;

    public int damageTaken;
    public boolean dead;

    private AbstractGameEffect cardToOrbEffect;

    public CreatureOrb(AbstractCreatureCard card) {
        this(card, (CardGroup)null);
    }

    public CreatureOrb(AbstractCreatureCard card, CardGroup source){

        this.creatureCard = card;
        this.creatureCard.orb = this;
        this.creatureCard.beginGlowing();

        this.damageTaken = 0;
        this.dead = false;

        this.channelAnimTimer = 0.5F;

        this.basePassiveAmount = this.creatureCard.baseAttack;
        this.passiveAmount = this.creatureCard.attack;

        this.baseEvokeAmount = this.creatureCard.baseHealth;
        this.evokeAmount = this.creatureCard.health;

        card.targetAngle = 0.0F;

        updateDescription();

        initialize(source);

    }

    private void initialize(CardGroup source) {
        if (source != null) {
            source.removeCard(this.creatureCard);

            switch (source.type) {
                case HAND:
                    this.cardToOrbEffect = new AddCardToOrbEffect(this.creatureCard, this, this.creatureCard.current_x, this.creatureCard.current_y, true);
                    break;
                case DRAW_PILE:
                    this.cardToOrbEffect = new AddCardToOrbEffect(this.creatureCard, this, AbstractDungeon.overlayMenu.combatDeckPanel.current_x + 100.0F * Settings.scale, AbstractDungeon.overlayMenu.combatDeckPanel.current_y + 100.0F * Settings.scale, false);
                    break;
                case DISCARD_PILE:
                    this.cardToOrbEffect = new AddCardToOrbEffect(this.creatureCard, this, AbstractDungeon.overlayMenu.discardPilePanel.current_x - 100.0F * Settings.scale, AbstractDungeon.overlayMenu.discardPilePanel.current_y + 100.0F * Settings.scale, false);
                    break;
                case EXHAUST_PILE:
                    this.creatureCard.unfadeOut();
                    this.cardToOrbEffect = new AddCardToOrbEffect(this.creatureCard, this, AbstractDungeon.overlayMenu.discardPilePanel.current_x - 100.0F * Settings.scale, AbstractDungeon.overlayMenu.exhaustPanel.current_y + 100.0F * Settings.scale, false);
                    break;
                default:
                    this.cardToOrbEffect = new AddCardToOrbEffect(this.creatureCard, this, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, true);
                    break;
            }
        } else {
            this.cardToOrbEffect = new AddCardToOrbEffect(this.creatureCard, this, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, true);
        }

        AbstractDungeon.effectsQueue.add(this.cardToOrbEffect);
        this.creatureCard.retain = false;
    }

    @Override
    public void update() {
        super.update();
        if(this.cardToOrbEffect == null || this.cardToOrbEffect.isDone){
            this.creatureCard.target_x = this.tX;
            this.creatureCard.target_y = this.tY;
            updateDescription();

            if (this.hb.hovered) {
                this.creatureCard.targetDrawScale = 1.0F;
            }else{
                if(this.creatureCard instanceof TheMoon){
                    this.creatureCard.targetDrawScale = 0.6F;
                }else{
                    this.creatureCard.targetDrawScale = 0.2F;
                }
            }
        }
        this.creatureCard.update();
    }

    @Override
    public void updateDescription() {
        applyFocus();

        if(this.creatureCard.isStatic){

            this.name = AbstractCreatureCard.MISC_DESCRIPTION[8];
            description = AbstractCreatureCard.MISC_DESCRIPTION[8] + " " + AbstractCreatureCard.MISC_DESCRIPTION[9] + this.creatureCard.attack + " " + AbstractCreatureCard.MISC_DESCRIPTION[10] + this.creatureCard.health;

        }else{

            this.name = this.creatureCard.name;
            description = this.creatureCard.name + " ";
            String extra = this.creatureCard.extraText();
            if(!extra.isEmpty())
                description += extra + " ";

            if (this.creatureCard.isEthereal)
                description += AbstractCreatureCard.MISC_DESCRIPTION[2] + " ";
            if (this.creatureCard.selfRetain)
                description += AbstractCreatureCard.MISC_DESCRIPTION[3] + " ";
            if (this.creatureCard.isFrail || this.creatureCard.tempFrail)
                description += AbstractCreatureCard.MISC_DESCRIPTION[4] + " ";
            if (this.creatureCard.bloodless && !this.creatureCard.current.contains(AbstractCreatureCard.Sigils.WORTHY_SACRIFICE))
                description += AbstractCreatureCard.MISC_DESCRIPTION[0] + " ";
            if (this.creatureCard.fleeting && !this.creatureCard.current.contains(AbstractCreatureCard.Sigils.UNKILLABLE))
                description += AbstractCreatureCard.MISC_DESCRIPTION[1] + " ";

            if (this.creatureCard.tribe != AbstractCreatureCard.CreatureTribe.NONE)
                description += AbstractCreatureCard.tribeText(this.creatureCard.tribe) + " ";
            if (this.creatureCard.costType != AbstractCreatureCard.CreatureCostType.NONE && this.creatureCard.extraCost > 0) {
                if (this.creatureCard.costType == AbstractCreatureCard.CreatureCostType.BLOOD) {
                    description += AbstractCreatureCard.MISC_DESCRIPTION[11] + this.creatureCard.extraCost + " ";
                } else {
                    description += AbstractCreatureCard.MISC_DESCRIPTION[12] + this.creatureCard.extraCost + " ";
                }
            }
            description += AbstractCreatureCard.MISC_DESCRIPTION[9] + this.creatureCard.attack + " " + AbstractCreatureCard.MISC_DESCRIPTION[10] + this.creatureCard.health + " ";

            String keywords = this.creatureCard.getSigils();
            if (!keywords.isEmpty())
                description += AbstractCreatureCard.sigilCardStrings.NAME + " : " + keywords + " ";

        }

        description = description.replace("!M!", "" + this.creatureCard.magicNumber);
        description = description.replace("!leshy:SecondMagic!", "" + this.creatureCard.secondMagicNumber);
        description = description.replace("leshy:", "");
        description = description.replace("_", " ");

    }

    @Override
    public void applyFocus() {
        this.creatureCard.applyPowers();
        passiveAmount = this.creatureCard.attack;
        evokeAmount = this.creatureCard.health - this.damageTaken;
        if(evokeAmount <= 0 && !this.dead) {
            this.dead = true;
            AbstractDungeon.actionManager.addToBottom(new CreatureSacrificeAction(this));
        }
    }

    @Override
    public void onEndOfTurn() {

        if(AbstractDungeon.player.orbs.indexOf(this) == 0)
            for(AbstractRelic r : AbstractDungeon.player.relics)
                if(r instanceof BearTrapRelic)
                    return;

        if(AbstractDungeon.player.orbs.indexOf(this) == AbstractDungeon.player.orbs.size()-1)
            for(AbstractRelic r : AbstractDungeon.player.relics)
                if(r instanceof OldDataRelic && ((OldDataRelic) r).left){
                    creatureCard.onPassive();
                    break;
                }

        creatureCard.onPassive();
    }

    @Override
    public void onEvoke() {
        creatureCard.onSacrifice();
    }

    @Override
    public void onStartOfTurn() {
        this.creatureCard.onStartOfTurn();
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount){

        if(this.creatureCard.current.contains(AbstractCreatureCard.Sigils.WATERBORNE) || info.type == DamageInfo.DamageType.HP_LOSS || this.dead)
            return damageAmount;
        if(damageAmount <= 0)
            return 0;

        if(this.creatureCard.current.contains(AbstractCreatureCard.Sigils.ARMORED) && !this.creatureCard.armoredTriggered){
            this.creatureCard.armoredTriggered = true;
            AbstractDungeon.effectList.add(new BlockImpactLineEffect(this.hb.cX, this.hb.cY));
            return 0;
        }

        LeshyMod.logger.info(this.name + " : " + damageAmount);

        this.creatureCard.onDamage();

        if(this.damageTaken + damageAmount >= this.creatureCard.health){

            int remaining = this.creatureCard.health - this.damageTaken;
            this.damageTaken = this.creatureCard.health;

            if(this.creatureCard.current.contains(AbstractCreatureCard.Sigils.LOOSE_TAIL) && !this.creatureCard.tailTriggered){

                this.creatureCard.tailTriggered = true;
                this.damageTaken = Math.max(this.creatureCard.health-6, 0);
                AbstractDungeon.actionManager.addToTop(new MoveOrbSlotAction(this, AbstractDungeon.player.orbs.size()-1));

            }else{

                this.dead = true;
                AbstractDungeon.actionManager.addToBottom(new CreatureSacrificeAction(this));

            }

            return damageAmount - remaining;
        }

        this.damageTaken += damageAmount;
        return 0;
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!this.hb.hovered && (this.cardToOrbEffect == null || this.cardToOrbEffect.isDone)) {
            renderActual(sb);
        }
    }

    public void renderActual(SpriteBatch sb){
        this.creatureCard.render(sb);
        if (!this.hb.hovered){
            renderText(sb);
        }
        this.hb.render(sb);
    }

    public void renderPreview(SpriteBatch sb) {
        if (this.hb.hovered && (this.cardToOrbEffect == null || this.cardToOrbEffect.isDone)) {
            renderActual(sb);
        }
    }

    @Override
    protected void renderText(SpriteBatch sb) {

        Color textColor;

        textColor = new Color(1.0F, 0.6F, 0.6F, this.c.a);

        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET,
                this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET+28.0F, textColor, this.fontScale);

        textColor = new Color(0.6F, 0.6F, 1.0F, this.c.a);

        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET,
                this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET-6.0F, textColor, this.fontScale);

    }

    @Override
    public void triggerEvokeAnimation() {
    }

    @Override
    public void playChannelSFX() {
    }

    @Override
    public AbstractOrb makeCopy() {
        CreatureOrb co = new CreatureOrb(this.creatureCard);
        co.passiveAmount = this.passiveAmount;
        co.basePassiveAmount = this.basePassiveAmount;
        return co;
    }
}
