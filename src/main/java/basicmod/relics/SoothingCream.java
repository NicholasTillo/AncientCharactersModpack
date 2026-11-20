package basicmod.relics;

import basicmod.character.CharFar;

import static basicmod.BasicMod.makeID;

//Burnout turns 1 card into a Burn instead of 3. The logic lives in Burnout.
public class SoothingCream extends BaseRelic {
    public static final String ID = makeID(SoothingCream.class.getSimpleName());

    public SoothingCream() {
        super(ID, SoothingCream.class.getSimpleName(), CharFar.Meta.CARD_COLOR, RelicTier.RARE, LandingSound.FLAT);
    }
}
