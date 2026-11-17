package basicmod.powers;

import basemod.BaseMod;
import basicmod.cards.common.Wisps;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnReceivePowerPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

import static basicmod.BasicMod.makeID;

public class Overheat extends BasePower{
    public static final String POWER_ID = makeID("Overheat");
    //The combat room of the most recent Overheat. Compare with AbstractDungeon.getCurrRoom() to ask "Overheated this combat?"
    public static AbstractRoom overheatedIn;

    public Overheat(AbstractCreature owner) {
        super(POWER_ID, AbstractPower.PowerType.BUFF, false, owner, 1);
    }
    @Override
    public void onInitialApplication() {
        overheatedIn = AbstractDungeon.getCurrRoom();
        //Draw 3
        // Gain 3 Energy
        // Gain 3 Strength.
        addToBot(new DrawCardAction(3));
        addToBot(new GainEnergyAction(3));
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 3), 3));
        addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, 3), 3));
        AbstractPlayer p = AbstractDungeon.player;
        for (CardGroup pile : new CardGroup[]{p.drawPile, p.discardPile}) {
            for (AbstractCard c : new ArrayList<>(pile.group)) {
                if (c instanceof Wisps && p.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    pile.moveToHand(c, pile);
                }
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new GainEnergyAction(3));

    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        if (!owner.hasPower(Timer.POWER_ID)) //Timer keeps Burnout away
            addToBot(new ApplyPowerAction(owner,owner,new Burnout(owner)));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

}

