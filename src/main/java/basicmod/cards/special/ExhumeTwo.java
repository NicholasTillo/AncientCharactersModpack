package basicmod.cards.special;

import basemod.BaseMod;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Chant reward card. Return 2 cards from the exhaust pile to hand; they cost 0 for the rest of combat.
public class ExhumeTwo extends BaseCard {
    public static final String ID = makeID(ExhumeTwo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.SELF,
            0
    );

    public ExhumeTwo() {
        super(ID, info);

        setMagic(2);
        setExhaust(true);
        tags.add(ChantTier.CHANT_RARE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Other Exhume Twos are left out, like the base game's Exhume, so they can't loop.
        addToBot(new SelectCardsAction(p.exhaustPile.group, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0], false,
                c -> !c.cardID.equals(ID), cards -> {
            for (AbstractCard c : cards) {
                c.unfadeOut();
                c.modifyCostForCombat(-9); //free for the rest of combat
                if (p.hand.size() < BaseMod.MAX_HAND_SIZE) p.exhaustPile.moveToHand(c, p.exhaustPile);
                else p.exhaustPile.moveToDiscardPile(c);
            }
        }));
    }
}
