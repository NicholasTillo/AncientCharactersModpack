package basicmod.powers;

import basicmod.actions.LoseHeatAction;
import basicmod.actions.ResetHeatAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Random;

import static basicmod.BasicMod.makeID;
import java.util.Collections;

public class Burnout extends BasePower {
    public static final String POWER_ID = makeID("Burnout");

    public Burnout(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, 1);
    }

    //Heat drops to 0 as soon as Burnout starts. GainHeatAction blocks Heat gain while this is active, so it stays at 0.
    @Override
    public void onInitialApplication() {
        addToBot(new ResetHeatAction());
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<Integer> slots = new ArrayList<>();
                for (int i = 0; i < AbstractDungeon.player.hand.size(); i++) slots.add(i);
                Collections.shuffle(slots, new Random(AbstractDungeon.cardRandomRng.randomLong()));
                for (int i = 0; i < Math.min(3, slots.size()); i++)
                    addToTop(new TransformCardInHandAction(slots.get(i), new Burn()));
                isDone = true;
            }
        });
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
