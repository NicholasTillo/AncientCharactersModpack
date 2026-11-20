package basicmod.cards.rare;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Comparator;

//Exhaust any number of cards from hand; get a random card of yours whose cost is their total, free this turn.
public class Transforphication extends BaseCard {
    public static final String ID = makeID(Transforphication.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            1
    );

    public Transforphication() {
        super(ID, info);

        setExhaust(true);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(99, cardStrings.EXTENDED_DESCRIPTION[0], true, true,
                c -> c.type != CardType.STATUS && c.type != CardType.CURSE, cards -> {
            if (cards.isEmpty()) return;
            int total = 0;
            for (AbstractCard c : cards) total += Math.max(0, c.costForTurn); //X-cost and unplayable count as 0
            AbstractCard made = randomCardCosting(total);
            //addToTop runs in reverse order: the exhausts happen first, then the new card arrives.
            if (made != null) {
                made.setCostForTurn(0);
                addToTop(new MakeTempCardInHandAction(made, 1));
            }
            //StSLib returns the chosen cards to hand after this callback, so exhaust them via queued actions.
            for (AbstractCard c : cards) addToTop(new ExhaustSpecificCardAction(c, p.hand));
        }));
    }

    //A random card from this character's pool costing exactly total; if none, the most expensive one below it.
    private static AbstractCard randomCardCosting(int total) {
        ArrayList<AbstractCard> pool = new ArrayList<>();
        int best = -1;
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.color != CharFar.Meta.CARD_COLOR || c.hasTag(CardTags.HEALING) || c.cost > total || c.cost < 0) continue;
            if (c.rarity != CardRarity.COMMON && c.rarity != CardRarity.UNCOMMON && c.rarity != CardRarity.RARE) continue;
            if (c.cost > best) { best = c.cost; pool.clear(); }
            if (c.cost == best) pool.add(c);
        }
        if (pool.isEmpty()) return null;
        pool.sort(Comparator.comparing(c -> c.cardID)); //same seed, same result
        return pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1)).makeCopy();
    }
}
