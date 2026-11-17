package basicmod.powers;

import basicmod.actions.ChantAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

//At the start of your turn, Chant amount times.
public class ChantMaxingPower extends BasePower {
    public static final String POWER_ID = makeID(ChantMaxingPower.class.getSimpleName());

    public ChantMaxingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ChantAction(amount));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
