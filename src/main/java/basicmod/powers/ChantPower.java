package basicmod.powers;

import basicmod.cards.special.ChantCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

//The Chant track. Every THRESHOLD stacks are spent to put a Chant card in hand.
public class ChantPower extends BasePower {
    public static final String POWER_ID = makeID("ChantPower");
    public static final int THRESHOLD = 4;

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

    private void payOut() {
        while (amount >= THRESHOLD) {
            amount -= THRESHOLD;
            flash();
            addToBot(new MakeTempCardInHandAction(new ChantCard()));
            amount2++;
        }
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (THRESHOLD - amount) + DESCRIPTIONS[1];
    }
}
