package basicmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static basicmod.BasicMod.makeID;

//Whenever the owner Burns out, apply amount Vulnerable to ALL enemies.
public class SteamWhistlePower extends BasePower {
    public static final String POWER_ID = makeID(SteamWhistlePower.class.getSimpleName());

    public SteamWhistlePower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    //Called for powers on the source of an ApplyPowerAction; Burnout is always applied by its owner.
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(Burnout.POWER_ID) && target == owner) {
            flash();
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped())
                    addToBot(new ApplyPowerAction(mo, owner, new VulnerablePower(mo, amount, false), amount));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
