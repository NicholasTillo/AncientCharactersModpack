package basicmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basicmod.BasicMod.makeID;

//Waxy Dexterity in reverse: cards grant amount less Block. Reduced by 1 at end of turn.
public class WaxFrail extends BasePower {
    private boolean justApplied;
    public static final String POWER_ID = makeID(WaxFrail.class.getSimpleName());

    public WaxFrail(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, amount);
        //Like the base game's Weak/Frail: if you give it to yourself on your own turn, skip the first tick so it lasts into next turn.
        justApplied = owner.isPlayer && !AbstractDungeon.actionManager.turnHasEnded;
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return Math.max(0, blockAmount - amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (justApplied) {
            justApplied = false;
            return;
        }
        if (!PotentWaxPower.active()) addToBot(new ReducePowerAction(owner, owner, this, 1)); //Potent Wax stops the tick
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
