package basicmod.powers;

import basicmod.actions.ChantRewardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

//The Chant track. Every THRESHOLD stacks are spent on a Chant reward: pick 1 of 3 Chant cards.
public class ChantPower extends BasePower {
    public static final String POWER_ID = makeID("ChantPower");
    public static final int THRESHOLD = 4;
    public int completedThisTurn = 0; //Chants completed this turn; amount2 counts the whole combat.

    public ChantPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void onInitialApplication() {
        payOut();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        payOut();
    }

    @Override
    public void atStartOfTurn() {
        completedThisTurn = 0;
    }

    private void payOut() {
        while (amount >= THRESHOLD) {
            amount -= THRESHOLD;
            flash();
            amount2++;
            addToBot(new ChantRewardAction(amount2));
            completedThisTurn++;
        }
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (THRESHOLD - amount) + DESCRIPTIONS[1];
    }
}
