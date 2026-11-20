package basicmod.potions;

import basicmod.actions.GainOverHeat;
import basicmod.character.CharFar;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static basicmod.BasicMod.makeID;

//Overheat.
public class HotSauce extends BasePotion {
    public static final String ID = makeID(HotSauce.class.getSimpleName());
    private static final Color LIQUID = new Color(0.85f, 0.1f, 0.05f, 1f);

    public HotSauce() {
        super(ID, 0, PotionRarity.RARE, PotionSize.BOTTLE, LIQUID, null, null); //no number, so Sacred Bark does nothing
        playerClass = CharFar.Meta.YOUR_CHARACTER;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            addToBot(new GainOverHeat());
    }
}
