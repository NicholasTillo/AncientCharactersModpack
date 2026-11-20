package basicmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basicmod.BasicMod.makeID;

//Wax buffs and debuffs (on anyone) stop going down each turn. The Wax powers check active() before reducing.
public class PotentWaxPower extends BasePower {
    public static final String POWER_ID = makeID(PotentWaxPower.class.getSimpleName());

    public PotentWaxPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, -1); //-1: no number shown, extra copies do nothing
    }

    public static boolean active() {
        return AbstractDungeon.player != null && AbstractDungeon.player.hasPower(POWER_ID);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
