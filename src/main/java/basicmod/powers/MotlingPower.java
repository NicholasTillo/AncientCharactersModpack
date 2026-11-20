package basicmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basicmod.BasicMod.makeID;

//Debuff: whenever this enemy's move is anything other than an attack, it takes amount damage.
public class MotlingPower extends BasePower {
    public static final String POWER_ID = makeID(MotlingPower.class.getSimpleName());

    public MotlingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, amount);
    }

    //For monsters this runs right before the monsters act, so intent is the move they're about to make.
    @Override
    public void atStartOfTurn() {
        if (owner instanceof AbstractMonster && !((AbstractMonster) owner).intent.name().startsWith("ATTACK")) {
            flash();
            addToBot(new DamageAction(owner, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
