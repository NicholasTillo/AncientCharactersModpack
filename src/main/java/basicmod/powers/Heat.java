package basicmod.powers;

import basicmod.actions.GainOverHeat;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

//Heat builds toward THRESHOLD (the owner's max HP). Reaching it spends the Heat and starts Overheating.
//Below that, Heat gives bonuses by tier; see tier().
public class Heat extends BasePower {
    public static final String POWER_ID = makeID("Heat");
    public int THRESHOLD;

    public Heat(AbstractCreature owner, int amount) {
        //The description needs THRESHOLD, so it is built here instead of in the BasePower constructor.
        super(POWER_ID, PowerType.BUFF, false, owner, null, amount, false);
        THRESHOLD = owner.maxHealth;
        updateDescription();
    }

    //0 = below a third of THRESHOLD, 1 = a third to two thirds, 2 = two thirds and up.
    public int tier() {
        return amount * 3 / THRESHOLD;
    }

    //Tier 1 and 2: attacks deal 1 more damage.
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL && tier() >= 1 ? damage + 1 : damage;
    }

    //Tier 2: 1 extra energy each turn.
    @Override
    public void atStartOfTurn() {
        if (tier() >= 2) {
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
        description = DESCRIPTIONS[0] + THRESHOLD + DESCRIPTIONS[1]
                + DESCRIPTIONS[2] + (tier() >= 1 ? 1 : 0)
                + DESCRIPTIONS[3] + (tier() >= 2 ? 1 : 0)
                + DESCRIPTIONS[4];
    }
}
