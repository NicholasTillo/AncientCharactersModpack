package basicmod.powers;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.DamageNumberEffect;

import static basicmod.BasicMod.makeID;

//The Clone: amount is its HP. It stands to your left with its own HP bar and soaks attack damage
//before your Block and HP. Leftover damage carries on to you. A new Clone replaces the old one.
public class ClonePower extends BasePower {
    public static final String POWER_ID = makeID(ClonePower.class.getSimpleName());
    private static final float OFFSET_X = 220f; //how far left of you the Clone stands (scaled)
    private static final Color TINT = new Color(0.6f, 0.8f, 1f, 0.7f); //ghostly blue
    private int maxHp;

    public ClonePower(AbstractCreature owner, int hp) {
        super(POWER_ID, PowerType.BUFF, false, owner, hp);
        maxHp = hp;
        updateDescription();
    }

    //Playing Clone again replaces the Clone instead of adding to it.
    @Override
    public void stackPower(int stackAmount) {
        amount = stackAmount;
        maxHp = stackAmount;
        updateDescription();
    }

    //Takes as much of the hit as it can; returns what's left for your Block and HP.
    private int soak(int damage) {
        int absorbed = Math.min(amount, damage);
        amount -= absorbed;
        flash();
        AbstractDungeon.effectList.add(new DamageNumberEffect(owner, cloneX(), owner.drawY + owner.hb_h, absorbed));
        if (amount <= 0) addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        updateDescription();
        return damage - absorbed;
    }

    private float cloneX() {
        return owner.drawX - OFFSET_X * Settings.scale;
    }

    //Draws a tinted copy of your character model, plus an HP bar under it.
    private void renderClone(SpriteBatch sb) {
        float x = cloneX();
        Skeleton sk = ReflectionHacks.getPrivate(owner, AbstractCreature.class, "skeleton");
        if (sk != null) {
            float oldX = sk.getX(), oldY = sk.getY();
            Color oldColor = sk.getColor().cpy();
            sk.setPosition(x + owner.animX, owner.drawY + owner.animY);
            sk.getColor().set(TINT);
            sk.updateWorldTransform();
            sb.end();
            CardCrawlGame.psb.begin();
            AbstractCreature.sr.draw(CardCrawlGame.psb, sk);
            CardCrawlGame.psb.end();
            sb.begin();
            sk.setPosition(oldX, oldY);
            sk.getColor().set(oldColor);
            sk.updateWorldTransform();
        }
        float w = owner.hb_w, h = 14f * Settings.scale;
        float barX = x - w / 2f, barY = owner.drawY - 30f * Settings.scale;
        sb.setColor(0f, 0f, 0f, 0.6f);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, barX, barY, w, h);
        sb.setColor(0.35f, 0.65f, 1f, 1f);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, barX, barY, w * amount / Math.max(1, maxHp), h);
        sb.setColor(Color.WHITE);
        FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, amount + "/" + maxHp, x, barY + h / 2f, Color.WHITE);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + "/" + maxHp + DESCRIPTIONS[1];
    }

    //Attack damage hits the Clone before Block. Monsters reuse one DamageInfo for every hit of a multi-hit attack,
    //so the original number is put back afterwards instead of being left lowered.
    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class SoakAttacks {
        private static int original = -1;

        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer p, DamageInfo info) {
            original = -1;
            AbstractPower pow = p.getPower(POWER_ID);
            if (!(pow instanceof ClonePower) || pow.amount <= 0) return;
            if (info.type != DamageInfo.DamageType.NORMAL || info.owner == null || info.owner == p || info.output <= 0) return;
            original = info.output;
            info.output = ((ClonePower) pow).soak(info.output);
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer p, DamageInfo info) {
            if (original >= 0) info.output = original;
            original = -1;
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "render")
    public static class DrawClone {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer p, SpriteBatch sb) {
            AbstractPower pow = p.getPower(POWER_ID);
            if (pow instanceof ClonePower && !p.isDead) ((ClonePower) pow).renderClone(sb);
        }
    }
}
