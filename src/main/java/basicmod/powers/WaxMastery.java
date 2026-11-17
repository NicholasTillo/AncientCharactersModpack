package basicmod.powers;

import basicmod.cards.CustomTags;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static basicmod.BasicMod.makeID;

//Wax cards (CustomTags.WAX) get +amount to their wax number.
//Damage and Block are handled here. Wax amounts on !M! come from BaseCard.applyPowers (CustomTags.WAX_MAGIC).
//Cards that work their number out by hand (Wax Ball Slide, the X-cost Wax Weakness) add bonus() themselves.
public class WaxMastery extends BasePower {
    public static final String POWER_ID = makeID("WaxMastery");

    public WaxMastery(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    //How much the player's wax numbers are currently buffed by. 0 without the power.
    public static int bonus() {
        AbstractPower pow = AbstractDungeon.player == null ? null : AbstractDungeon.player.getPower(POWER_ID);
        return pow == null ? 0 : pow.amount;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return type == DamageInfo.DamageType.NORMAL && card.hasTag(CustomTags.WAX) ? damage + amount : damage;
    }

    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        return card.hasTag(CustomTags.WAX) ? blockAmount + amount : blockAmount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
