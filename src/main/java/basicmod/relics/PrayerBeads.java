package basicmod.relics;

import basicmod.actions.ChantAction;
import basicmod.character.CharFar;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basicmod.BasicMod.makeID;

//At the start of each combat, Chant 1.
public class PrayerBeads extends BaseRelic {
    public static final String ID = makeID(PrayerBeads.class.getSimpleName());

    public PrayerBeads() {
        super(ID, PrayerBeads.class.getSimpleName(), CharFar.Meta.CARD_COLOR, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ChantAction(2));
    }
}
