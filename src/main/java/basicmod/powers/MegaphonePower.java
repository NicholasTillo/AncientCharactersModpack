package basicmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static basicmod.BasicMod.makeID;

//Chant rewards let you pick amount more card(s). Read by ChantRewardAction.
public class MegaphonePower extends BasePower {
    public static final String POWER_ID = makeID(MegaphonePower.class.getSimpleName());

    public MegaphonePower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    public static int extraPicks() {
        AbstractPower pow = AbstractDungeon.player.getPower(POWER_ID);
        return pow == null ? 0 : pow.amount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (amount + 1) + DESCRIPTIONS[1];
    }
}
