package basicmod.cards.special;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;

//The card created by the Chant track. The effect below is a placeholder.
public class IntangebleCard extends BaseCard {
    public static final String ID = makeID(IntangebleCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.SELF,
            0
    );
    private static final int INT_NUMBER = 1;
    private static final int INT_NUMBER_UPG = 1;

    public IntangebleCard() {
        super(ID, info);

        setMagic(INT_NUMBER, INT_NUMBER_UPG);
        setExhaust(true);
        tags.add(ChantTier.CHANT_RARE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new IntangiblePower(p, magicNumber)));
    }
}
