package basicmod.cards.common;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ExpressiveIteration extends BaseCard {
    public static final String ID = makeID(ExpressiveIteration.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    public ExpressiveIteration() {
        super(ID, info);

        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Wrapped in an action so the top 3 are read when this resolves, not when the card is played.
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                //The top of the draw pile is the end of the list.
                int size = p.drawPile.size();
                ArrayList<AbstractCard> top = new ArrayList<>(p.drawPile.group.subList(Math.max(0, size - 3), size));
                if (top.isEmpty()) return;

                //Pick one for the hand, then one of the rest to Exhaust. Whatever is left stays on top.
                addToTop(new SelectCardsAction(top, 1, cardStrings.EXTENDED_DESCRIPTION[0], toHand -> {
                    for (AbstractCard c : toHand) {
                        top.remove(c);
                        if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                            p.drawPile.moveToDiscardPile(c);
                            p.createHandIsFullDialog();
                        } else {
                            p.drawPile.moveToHand(c, p.drawPile);
                        }
                    }
                    if (!top.isEmpty()) {
                        addToTop(new SelectCardsAction(top, 1, cardStrings.EXTENDED_DESCRIPTION[1],
                                toExhaust -> toExhaust.forEach(p.drawPile::moveToExhaustPile)));
                    }
                }));
            }
        });
    }
}
