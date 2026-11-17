package basicmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

//Heat gained is doubled (GainHeatAction) and Overheat doesn't lead to Burnout (Overheat.atEndOfRound).
public class TheWorldAflamePower extends BasePower {
    public static final String POWER_ID = makeID(TheWorldAflamePower.class.getSimpleName());

    public TheWorldAflamePower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, -1); //-1 hides the stack number
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
