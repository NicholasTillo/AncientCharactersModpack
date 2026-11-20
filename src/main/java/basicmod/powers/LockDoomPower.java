package basicmod.powers;

import basicmod.actions.ChantAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

//On an enemy: when it dies, the player draws 3, gains 3 Energy and Chants 3 times (per stack).
//A debuff, so Artifact blocks it.
public class LockDoomPower extends BasePower {
    public static final String POWER_ID = makeID(LockDoomPower.class.getSimpleName());
    private static final int AMOUNT = 3;

    public LockDoomPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, amount);
    }

    @Override
    public void onDeath() {
        for (int s = 0; s < amount; s++) {
            addToBot(new DrawCardAction(AMOUNT));
            addToBot(new GainEnergyAction(AMOUNT));
            for (int i = 0; i < AMOUNT; i++) addToBot(new ChantAction(1));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
