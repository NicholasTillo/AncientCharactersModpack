package basicmod.relics;

import basicmod.character.CharFar;
import basicmod.powers.WaxFrail;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basicmod.BasicMod.makeID;

//Gain 1 Energy each turn. At the start of each combat, gain 3 Wax Frail.
public class WaxEffigy extends BaseRelic {
    public static final String ID = makeID(WaxEffigy.class.getSimpleName());
    private static final int FRAIL = 3;

    public WaxEffigy() {
        super(ID, WaxEffigy.class.getSimpleName(), CharFar.Meta.CARD_COLOR, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new ApplyPowerAction(p, p, new WaxFrail(p, FRAIL), FRAIL));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + FRAIL + DESCRIPTIONS[1];
    }
}
