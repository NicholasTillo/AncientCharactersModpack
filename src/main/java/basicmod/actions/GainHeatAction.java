package basicmod.actions;

import basicmod.powers.Burnout;
import basicmod.powers.Heat;
import basicmod.powers.TheWorldAflamePower;
import basicmod.relics.Bellows;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

//The one place the player gains Heat. Use with: addToBot(new GainHeatAction(5));
//Anything that should change all Heat gain (doubling, caps, etc.) goes here.
public class GainHeatAction extends AbstractGameAction {
    private final boolean fromCard; //Bellows only boosts Heat from cards; relics pass false.

    public GainHeatAction(int amount) {
        this(amount, true);
    }

    public GainHeatAction(int amount, boolean fromCard) {
        this.amount = amount;
        this.fromCard = fromCard;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hasPower(Burnout.POWER_ID)) { //No Heat while burnt out.
            int gain = fromCard && p.hasRelic(Bellows.ID) ? amount + amount / 2 : amount; //+50%, rounded down
            if (p.hasPower(TheWorldAflamePower.POWER_ID)) gain *= 2;
            addToTop(new ApplyPowerAction(p, p, new Heat(p, gain), gain));
        }
        isDone = true;
    }
}
