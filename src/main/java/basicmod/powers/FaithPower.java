package basicmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static basicmod.BasicMod.makeID;

//Whenever the owner Chants, gain Block.
public class FaithPower extends BasePower {
    public static final String POWER_ID = makeID(FaithPower.class.getSimpleName());

    public FaithPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    //Every Chant goes through ChantAction, which applies ChantPower from the player. power.amount is how many Chants.
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(ChantPower.POWER_ID) && target == owner) {
            flash();
            addToBot(new GainBlockAction(owner, owner, amount * power.amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
