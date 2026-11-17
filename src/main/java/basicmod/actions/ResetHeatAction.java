package basicmod.actions;

import basicmod.powers.Heat;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

//The one place the player gains Heat. Use with: addToBot(new GainHeatAction(5));
//Anything that should change all Heat gain (doubling, caps, etc.) goes here.
public class ResetHeatAction extends AbstractGameAction {
    public ResetHeatAction() {}

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new RemoveSpecificPowerAction(p,p, Heat.POWER_ID));
        isDone = true;
    }
}
