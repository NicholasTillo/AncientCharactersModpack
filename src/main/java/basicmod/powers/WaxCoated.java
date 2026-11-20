package basicmod.powers;

import basicmod.relics.BeeswaxSeal;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

public class WaxCoated extends BasePower{
    public static final String POWER_ID = makeID(WaxCoated.class.getSimpleName());

    public WaxCoated(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, amount);
        justApplied = !owner.isPlayer && AbstractDungeon.player.hasRelic(BeeswaxSeal.ID);
    }
    private boolean justApplied; //Beeswax Seal: stays for one extra tick on enemies

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new DamageAction(owner, new DamageInfo(owner, amount)));
        if (justApplied) justApplied = false;
        else addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[1];
    }
}

