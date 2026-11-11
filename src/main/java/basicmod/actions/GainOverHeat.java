package basicmod.actions;

import basicmod.powers.ChantPower;
import basicmod.powers.MeltingBlockPower;
import basicmod.powers.Overheat;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainOverHeat extends AbstractGameAction {
    public GainOverHeat() {
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new Overheat(p), amount));
        isDone = true;
    }
}
