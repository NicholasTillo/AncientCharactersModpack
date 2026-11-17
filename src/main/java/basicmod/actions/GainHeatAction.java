package basicmod.actions;

import basicmod.powers.Burnout;
import basicmod.powers.Heat;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

//The one place the player gains Heat. Use with: addToBot(new GainHeatAction(5));
//Anything that should change all Heat gain (doubling, caps, etc.) goes here.
public class GainHeatAction extends AbstractGameAction {
    public GainHeatAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hasPower(Burnout.POWER_ID)) { //No Heat while burnt out.
            addToTop(new ApplyPowerAction(p, p, new Heat(p, amount), amount));
        }
        isDone = true;
    }
}
