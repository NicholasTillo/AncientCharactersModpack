package basicmod.actions;

import basicmod.powers.ChantPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

//Advances the Chant track. Use from a card with: addToBot(new ChantAction(1));
public class ChantAction extends AbstractGameAction {
    public ChantAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new ChantPower(p, amount), amount));
        isDone = true;
    }
}
