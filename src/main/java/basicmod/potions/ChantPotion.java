package basicmod.potions;

import basicmod.actions.ChantAction;
import basicmod.character.CharFar;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static basicmod.BasicMod.makeID;

//Chant 3 times (6 with Sacred Bark).
public class ChantPotion extends BasePotion {
    public static final String ID = makeID(ChantPotion.class.getSimpleName());
    private static final Color LIQUID = new Color(0.55f, 0.3f, 0.8f, 1f);

    public ChantPotion() {
        super(ID, 3, PotionRarity.UNCOMMON, PotionSize.SPHERE, LIQUID, null, null);
        playerClass = CharFar.Meta.YOUR_CHARACTER;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;
        for (int i = 0; i < potency; i++) addToBot(new ChantAction(1)); //one at a time, like playing 3 Chant cards
    }
}
