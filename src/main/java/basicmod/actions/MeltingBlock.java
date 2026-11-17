package basicmod.actions;

import basicmod.powers.ChantPower;
import basicmod.powers.MeltingBlockPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MeltingBlock extends AbstractGameAction {
    public MeltingBlock(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new MeltingBlockPower(p, amount), amount));
        isDone = true;
    }
}
