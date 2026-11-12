package basicmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basicmod.BasicMod.makeID;

//At the end of combat, heal if the owner Overheated at any point this combat (same shape as the base game's Self Repair).
public class LetHimCookPower extends BasePower {
    public static final String POWER_ID = makeID("LetHimCookPower");

    public LetHimCookPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void onVictory() {
        if (owner.currentHealth > 0 && Overheat.overheatedIn == AbstractDungeon.getCurrRoom()) {
            owner.heal(amount);
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
