package basicmod.relics;

import basicmod.character.CharFar;

import static basicmod.BasicMod.makeID;

//Heat gained from cards is increased by 50%. The logic lives in GainHeatAction.
public class Bellows extends BaseRelic {
    public static final String ID = makeID(Bellows.class.getSimpleName());

    public Bellows() {
        super(ID, Bellows.class.getSimpleName(), CharFar.Meta.CARD_COLOR, RelicTier.SHOP, LandingSound.HEAVY);
    }
}
