package basicmod.powers;

import basicmod.actions.ChantRewardAction;
import basicmod.cards.special.ChantTier;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.Comparator;

import static basicmod.BasicMod.makeID;

//The Chant track. Every THRESHOLD stacks are spent on a Chant reward: pick 1 of 3 Chant cards.
public class PriestFormPower extends BasePower {
    public static final String POWER_ID = makeID(PriestFormPower.class.getSimpleName());

    public static final int THRESHOLD = 4;
    public ArrayList<AbstractCard> pool;
    public int completedThisTurn = 0; //Chants completed this turn; amount2 counts the whole combat.

    public PriestFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
        pool = new ArrayList<>();
        for (AbstractCard card : CardLibrary.getAllCards())
            if (card.hasTag(ChantTier.CHANT_COMMON) || card.hasTag(ChantTier.CHANT_UNCOMMON) || card.hasTag(ChantTier.CHANT_RARE))
                pool.add(card);
        pool.sort(Comparator.comparing(card -> card.cardID)); //same seed, same result
    }


    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    @Override
    public void atStartOfTurn() {
        AbstractCard c = pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1)).makeCopy();
        addToBot(new MakeTempCardInHandAction(c, 1));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
