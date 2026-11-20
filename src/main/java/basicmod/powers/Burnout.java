package basicmod.powers;

import basicmod.actions.LoseHeatAction;
import basicmod.actions.ResetHeatAction;
import basicmod.relics.SoothingCream;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
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
    public static final String POWER_ID = makeID(Burnout.class.getSimpleName());

    public Burnout(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, 1);
    }

    //Heat drops to 0 as soon as Burnout starts. GainHeatAction blocks Heat gain while this is active, so it stays at 0.
    @Override
    //Overheat applies this at end of round, but the game runs the start-of-turn hooks before that
    //apply resolves, so atStartOfTurnPostDraw would only fire a turn late. Queuing the Burns here
    //puts them after the turn's DrawCardAction, which is already in the queue by now.
    public void onInitialApplication() {
        addToBot(new ResetHeatAction());
        //Lose 3 Strength for this turn, the same way Piercing Wail does it (Artifact blocks the loss, so skip the give-back then).
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -3), -3));
        if (!owner.hasPower(ArtifactPower.POWER_ID))
            addToBot(new ApplyPowerAction(owner, owner, new GainStrengthPower(owner, 3), 3));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<Integer> slots = new ArrayList<>();
                for (int i = 0; i < AbstractDungeon.player.hand.size(); i++) slots.add(i);
                Collections.shuffle(slots, new Random(AbstractDungeon.cardRandomRng.randomLong()));
                int burns = AbstractDungeon.player.hasRelic(SoothingCream.ID) ? 1 : 3;
                for (int i = 0; i < Math.min(burns, slots.size()); i++)
                    addToTop(new TransformCardInHandAction(slots.get(i), new Burn()));
                isDone = true;
            }
        });
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
