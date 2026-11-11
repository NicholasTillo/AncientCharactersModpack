package basicmod.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnReceivePowerPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static basicmod.BasicMod.makeID;

public class Overheat extends BasePower{
    public static final String POWER_ID = makeID("Overheat");

    public Overheat(AbstractCreature owner) {
        super(POWER_ID, AbstractPower.PowerType.BUFF, false, owner, 1);
    }
    @Override
    public void onInitialApplication() {
        //Draw 3
        // Gain 3 Energy
        // Gain 3 Strength.
        addToBot(new DrawCardAction(3));
        addToBot(new GainEnergyAction(3));
        addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, 3), 3));
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new GainEnergyAction(3));

    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        addToBot(new ApplyPowerAction(owner,owner,new Burnout(owner)));

    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

}

