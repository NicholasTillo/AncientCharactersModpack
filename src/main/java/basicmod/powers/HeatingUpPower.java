package basicmod.powers;

import basicmod.actions.GainHeatAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static basicmod.BasicMod.makeID;

//Whenever you play a card, gain amount Heat.
public class HeatingUpPower extends BasePower {
    public static final String POWER_ID = makeID("HeatingUpPower");

    public HeatingUpPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        addToBot(new GainHeatAction(amount));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
