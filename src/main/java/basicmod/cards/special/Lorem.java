package basicmod.cards.special;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Chant reward card.
public class Lorem extends BaseCard {
    public static final String ID = makeID(Lorem.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.SELF,
            0
    );

    public Lorem() {
        super(ID, info);

        setMagic(8, 4);
        setExhaust(true);
        tags.add(ChantTier.CHANT_UNCOMMON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, magicNumber));
    }
}
