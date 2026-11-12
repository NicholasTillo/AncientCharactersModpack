package basicmod.cards.special;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//A card created by the Chant track.
public class TutorTwo extends BaseCard {
    public static final String ID = makeID(TutorTwo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.SELF,
            0
    );

    public TutorTwo() {
        super(ID, info);

        setExhaust(true);
        tags.add(ChantTier.CHANT_RARE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //StSLib's FetchAction opens the card picker on the draw pile and moves the chosen card to the hand.
        addToBot(new FetchAction(p.drawPile, 1, fetched -> {
            for (AbstractCard c : fetched) {
                if (c.canUpgrade()) c.upgrade();
                c.setCostForTurn(0);
            }
        }));
    }
}
