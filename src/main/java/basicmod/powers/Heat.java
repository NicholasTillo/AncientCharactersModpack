package basicmod.powers;

import basicmod.actions.GainOverHeat;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

//Heat builds toward THRESHOLD (the owner's max HP). Reaching it spends the Heat and starts Overheating.
//Below that, Heat gives bonuses by tier; see tier().
public class Heat extends BasePower {
    public static final String POWER_ID = makeID(Heat.class.getSimpleName());
    public int THRESHOLD;

    public Heat(AbstractCreature owner, int amount) {
        //The description needs THRESHOLD, so it is built here instead of in the BasePower constructor.
        super(POWER_ID, PowerType.BUFF, false, owner, null, amount, false);
        THRESHOLD = owner.maxHealth;
        updateDescription();
    }

    //0 = below half of THRESHOLD, 1 = half and up.
    public int tier() {
        return amount * 2 / THRESHOLD;
    }

    //Tier 1: attacks deal 1 more damage.
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL && tier() >= 1 ? damage + 1 : damage;
    }

    //Tier 1: 1 extra energy each turn.
    @Override
    public void atStartOfTurn() {
        if (tier() >= 1 && !owner.hasPower(Overheat.POWER_ID)) {
            flash();
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void onInitialApplication() {
        checkHeat();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        checkHeat();
    }

    private void checkHeat() {
        while (amount >= THRESHOLD) {
            amount -= THRESHOLD;
            flash();
            addToBot(new GainOverHeat());
        }
        updateDescription();
    }

    @Override
    public void updateDescription() {
        //(THRESHOLD + 1) / 2 is the lowest Heat where tier() reaches 1.
        description = DESCRIPTIONS[0] + (THRESHOLD + 1) / 2 + DESCRIPTIONS[1] + THRESHOLD + DESCRIPTIONS[2];
    }
}
