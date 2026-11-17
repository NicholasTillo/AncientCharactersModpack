package basicmod.powers;

import basicmod.actions.GainHeatAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

public class KindlingPower extends BasePower{
    public static final String POWER_ID = makeID("KindlingPower");

    public KindlingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        Heat heatP= ((Heat) owner.getPower("Heat"));
        if(heatP != null){
            if(heatP.tier() >= 1){
                addToBot(new GainBlockAction(owner, 4));
            }
        else{
                addToBot(new GainHeatAction(10));
            }
        }

    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[1];
    }
}

