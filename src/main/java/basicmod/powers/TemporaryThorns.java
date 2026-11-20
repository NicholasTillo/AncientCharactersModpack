package basicmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static basicmod.BasicMod.makeID;

//Apply alongside ThornsPower: takes that much Thorns back at the start of your next turn (like Flex's Lose Strength).
public class TemporaryThorns extends BasePower {
    public static final String POWER_ID = makeID(TemporaryThorns.class.getSimpleName());

    public TemporaryThorns(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ReducePowerAction(owner, owner, ThornsPower.POWER_ID, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
