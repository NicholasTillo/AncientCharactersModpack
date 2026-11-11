package basicmod.actions;

import basicmod.powers.MeltingBlockPower;
import basicmod.powers.MeltingStrengthPower;
import basicmod.powers.WaxyDexterity;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainWaxyDex extends AbstractGameAction {
    public GainWaxyDex(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new WaxyDexterity(p, amount), amount));
        isDone = true;
    }
}
