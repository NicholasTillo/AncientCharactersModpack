package basicmod.powers;

import basicmod.relics.BeeswaxSeal;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basicmod.BasicMod.makeID;

public class DebuffWaxWeakness extends BasePower{
    private boolean justApplied;
    public static final String POWER_ID = makeID(DebuffWaxWeakness.class.getSimpleName());

    public DebuffWaxWeakness(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, amount);
        //Like the base game's Weak/Frail: if you give it to yourself on your own turn, skip the first tick so it lasts into next turn.
        //Beeswax Seal: on enemies, skip the first tick too.
        justApplied = owner.isPlayer ? !AbstractDungeon.actionManager.turnHasEnded : AbstractDungeon.player.hasRelic(BeeswaxSeal.ID);
    }
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage - amount : damage;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (justApplied) {
            justApplied = false;
            return;
        }
        if (!PotentWaxPower.active()) addToBot(new ReducePowerAction(owner, owner, this, 1)); //Potent Wax stops the tick
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[1];
    }
}

