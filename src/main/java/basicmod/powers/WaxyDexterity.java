package basicmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

public class WaxyDexterity extends BasePower{
    public static final String POWER_ID = makeID(WaxyDexterity.class.getSimpleName());

    public WaxyDexterity(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount + amount;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!PotentWaxPower.active()) addToBot(new ReducePowerAction(owner, owner, this, 1)); //Potent Wax stops the tick
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[1];
    }
}

