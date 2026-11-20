package basicmod.relics;

import basicmod.cards.special.ChantTier;
import basicmod.character.CharFar;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basicmod.BasicMod.makeID;

//Cards rewarded by completing a Chant are Retained.
public class Hymnal extends BaseRelic {
    public static final String ID = makeID(Hymnal.class.getSimpleName());

    public Hymnal() {
        super(ID, Hymnal.class.getSimpleName(), CharFar.Meta.CARD_COLOR, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    //Runs before the end-of-turn discard, which skips cards with retain set (then clears the flag).
    @Override
    public void onPlayerEndTurn() {
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            if (c.hasTag(ChantTier.CHANT_COMMON) || c.hasTag(ChantTier.CHANT_UNCOMMON) || c.hasTag(ChantTier.CHANT_RARE))
                c.retain = true;
    }
}
