package basicmod.potions;

import basicmod.character.CharFar;
import basicmod.powers.MeltingStrengthPower;
import basicmod.powers.WaxyDexterity;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static basicmod.BasicMod.makeID;

//Gain 3 Wax Strength and 3 Waxy Dexterity.
public class WaxLotion extends BasePotion {
    public static final String ID = makeID(WaxLotion.class.getSimpleName());
    private static final Color LIQUID = new Color(0.95f, 0.85f, 0.55f, 1f);

    public WaxLotion() {
        super(ID, 3, PotionRarity.COMMON, PotionSize.JAR, LIQUID, null, null);
        playerClass = CharFar.Meta.YOUR_CHARACTER;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1] + potency + DESCRIPTIONS[2];
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new MeltingStrengthPower(p, potency), potency));
        addToBot(new ApplyPowerAction(p, p, new WaxyDexterity(p, potency), potency));
    }
}
