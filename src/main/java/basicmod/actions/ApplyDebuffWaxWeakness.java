package basicmod.actions;

import basicmod.powers.DebuffWaxWeakness;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ApplyDebuffWaxWeakness extends AbstractGameAction {
    public ApplyDebuffWaxWeakness(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(target, p, new DebuffWaxWeakness(p, amount), amount));
        isDone = true;
    }
}
