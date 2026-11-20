package basicmod.relics;

import basicmod.character.CharFar;

import static basicmod.BasicMod.makeID;

//Wax Coated and Wax Weakness on enemies don't decrease the first turn they're applied.
//The logic lives in WaxCoated and DebuffWaxWeakness, which check for this relic.
public class BeeswaxSeal extends BaseRelic {
    public static final String ID = makeID(BeeswaxSeal.class.getSimpleName());

    public BeeswaxSeal() {
        super(ID, BeeswaxSeal.class.getSimpleName(), CharFar.Meta.CARD_COLOR, RelicTier.RARE, LandingSound.FLAT);
    }
}
