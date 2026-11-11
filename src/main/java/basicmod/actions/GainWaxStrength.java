package basicmod.actions;

import basicmod.powers.MeltingBlockPower;
import basicmod.powers.MeltingStrengthPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainWaxStrength extends AbstractGameAction {
    public GainWaxStrength(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new MeltingStrengthPower(p, amount), amount));
        isDone = true;
    }
}
