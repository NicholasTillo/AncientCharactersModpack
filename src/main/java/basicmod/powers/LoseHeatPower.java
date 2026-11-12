package basicmod.powers;

import basicmod.actions.LoseHeatAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

//LoseStrengthPower, but for Heat: at the end of the turn, lose this much Heat and remove this power.
public class LoseHeatPower extends BasePower {
    public static final String POWER_ID = makeID("LoseHeatPower");

    public LoseHeatPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new LoseHeatAction(amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
