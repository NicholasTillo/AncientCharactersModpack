package basicmod.powers;

import basicmod.actions.GainOverHeat;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

//While active: Overheat every turn with no Burnout afterwards (see Overheat.atEndOfRound). The owner dies when it runs out.
public class Timer extends BasePower{
    public static final String POWER_ID = makeID("Timer");

    public Timer(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new GainOverHeat());
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    //ReducePowerAction removes the power at 0, which lands here.
    @Override
    public void onRemove() {
        addToTop(new LoseHPAction(owner, owner, 99999));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[1];
    }
}
