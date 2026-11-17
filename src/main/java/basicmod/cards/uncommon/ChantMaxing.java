package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.ChantMaxingPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChantMaxing extends BaseCard {
    public static final String ID = makeID(ChantMaxing.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public ChantMaxing() {
        super(ID, info);

        setMagic(1);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ChantMaxingPower(p, magicNumber), magicNumber));
    }
}
