package basicmod.relics;

import basicmod.character.CharFar;
import basicmod.powers.MeltingStrengthPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basicmod.BasicMod.makeID;

//At the start of each combat, gain 2 Wax Strength.
public class ReinforcedSnuffer extends BaseRelic {
    public static final String ID = makeID(ReinforcedSnuffer.class.getSimpleName());
    private static final int AMOUNT = 2;

    public ReinforcedSnuffer() {
        super(ID, ReinforcedSnuffer.class.getSimpleName(), CharFar.Meta.CARD_COLOR, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new ApplyPowerAction(p, p, new MeltingStrengthPower(p, AMOUNT), AMOUNT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }
}
