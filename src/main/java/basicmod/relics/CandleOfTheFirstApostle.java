package basicmod.relics;

import basicmod.actions.GainHeatAction;
import basicmod.character.CharFar;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static basicmod.BasicMod.makeID;

//Boss upgrade of Smoldering Wick (replaces it, like Black Blood replaces Burning Blood).
//More Heat at end of turn and per HP lost, plus a quarter of your Max HP as Heat when combat starts.
public class CandleOfTheFirstApostle extends BaseRelic {
    public static final String ID = makeID(CandleOfTheFirstApostle.class.getSimpleName());
    private static final int HEAT_PER_TURN = 8;
    private static final int HEAT_PER_DAMAGE = 3;
    private static final int START_PERCENT = 25;

    public CandleOfTheFirstApostle() {
        super(ID, CandleOfTheFirstApostle.class.getSimpleName(), CharFar.Meta.CARD_COLOR, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(SmolderingWick.ID);
    }

    //Takes Smoldering Wick's slot instead of being added alongside it.
    @Override
    public void obtain() {
        AbstractPlayer p = AbstractDungeon.player;
        for (int i = 0; i < p.relics.size(); i++) {
            if (p.relics.get(i).relicId.equals(SmolderingWick.ID)) {
                instantObtain(p, i, true);
                return;
            }
        }
        super.obtain();
    }

    @Override
    public void atBattleStart() {
        gainHeat(AbstractDungeon.player.maxHealth * START_PERCENT / 100);
    }

    @Override
    public void onPlayerEndTurn() {
        gainHeat(HEAT_PER_TURN);
    }

    @Override
    public void wasHPLost(int damageAmount) {
        if (damageAmount > 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            gainHeat(HEAT_PER_DAMAGE * damageAmount);
        }
    }

    private void gainHeat(int amount) {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new GainHeatAction(amount, false));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + HEAT_PER_TURN + DESCRIPTIONS[1] + HEAT_PER_DAMAGE + DESCRIPTIONS[2] + START_PERCENT + DESCRIPTIONS[3];
    }
}
