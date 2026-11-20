package basicmod.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

import static basicmod.BasicMod.makeID;

//At the start of your turn, add amount Waxy (Ethereal + Exhaust) copies of the first card you played last turn to your hand.
public class SelfPortraitPower extends BasePower {
    public static final String POWER_ID = makeID(SelfPortraitPower.class.getSimpleName());
    private AbstractCard firstLastTurn; //Remembered at end of turn, because the game's own list is wiped before the next turn starts.

    public SelfPortraitPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        ArrayList<AbstractCard> played = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        firstLastTurn = played.isEmpty() ? null : played.get(0);
    }

    @Override
    public void atStartOfTurn() {
        if (firstLastTurn == null) return;
        flash();
        AbstractCard original = firstLastTurn;
        for (int i = 0; i < amount; i++) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractCard copy = makeWaxy(original.makeStatEquivalentCopy());
                    if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(copy));
                    } else {
                        AbstractDungeon.player.createHandIsFullDialog();
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(copy));
                    }
                }
            });
        }
    }

    //Waxy = Ethereal and Exhaust.
    //ponytail: appends to the card's text once; a card that rebuilds its own text mid-combat loses the two words (the effect stays). Use a BaseMod AbstractCardModifier if that matters.
    public static AbstractCard makeWaxy(AbstractCard c) {
        //Check the text too: BaseCard copies carry the text over but not the flags, so a copy of a Waxy card would add the words again.
        String extra = "";
        c.isEthereal = true;
        if (!c.rawDescription.contains("Ethereal.")) extra += " NL Ethereal.";
        if (c.type != AbstractCard.CardType.POWER) {
            c.exhaust = true;
            if (!c.rawDescription.contains("Exhaust.")) extra += " NL Exhaust.";
        }
        if (!extra.isEmpty()) {
            c.rawDescription += extra;
            c.initializeDescription();
        }
        return c;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
