package basicmod.cards.common;

import basicmod.actions.ChantAction;
import basicmod.cards.BaseCard;
import basicmod.cards.special.ChantTier;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Draw cards; if any drawn card is a Chant card, Chant once.
public class AbyssalPeering extends BaseCard {
    public static final String ID = makeID(AbyssalPeering.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    public AbyssalPeering() {
        super(ID, info);

        setMagic(3, 1);
    }

    //A Chant card: one whose text uses the Chant keyword, or a Chant reward card.
    //ponytail: keyword-in-text check; cards that only say "Chant" without ${modID}: won't count. Add a CHANT tag if that matters.
    public static boolean isChantCard(AbstractCard c) {
        return c.rawDescription.contains(makeID("Chant"))
                || c.hasTag(ChantTier.CHANT_COMMON) || c.hasTag(ChantTier.CHANT_UNCOMMON) || c.hasTag(ChantTier.CHANT_RARE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : DrawCardAction.drawnCards) {
                    if (isChantCard(c)) {
                        addToTop(new ChantAction(1));
                        break;
                    }
                }
                isDone = true;
            }
        }));
    }
}
