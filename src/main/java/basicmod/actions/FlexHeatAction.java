package basicmod.actions;

import basicmod.powers.LoseHeatPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

//Flex, but for Heat: gain Heat now, lose the same amount at the end of the turn.
public class FlexHeatAction extends AbstractGameAction {
    public FlexHeatAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new LoseHeatPower(p, amount), amount));
        addToTop(new GainHeatAction(amount));
        isDone = true;
    }
}
