package basicmod.relics;

import basicmod.actions.GainHeatAction;
import basicmod.character.CharFar;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static basicmod.BasicMod.makeID;

//Gain Heat at the end of each turn, and for every point of HP lost in combat.
public class SmolderingWick extends BaseRelic {
    public static final String ID = makeID("SmolderingWick");
    private static final int HEAT_PER_TURN = 5;
    private static final int HEAT_PER_DAMAGE = 2;

    public SmolderingWick() {
        super(ID, "SmolderingWick", CharFar.Meta.CARD_COLOR, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        gainHeat(HEAT_PER_TURN);
    }

    @Override
    public void wasHPLost(int damageAmount) {
        //HP can also be lost in events; Heat only exists in combat.
        if (damageAmount > 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            gainHeat(HEAT_PER_DAMAGE * damageAmount);
        }
    }

    private void gainHeat(int amount) {
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new GainHeatAction(amount));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + HEAT_PER_TURN + DESCRIPTIONS[1] + HEAT_PER_DAMAGE + DESCRIPTIONS[2];
    }
}
