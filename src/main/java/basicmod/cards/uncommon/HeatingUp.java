package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.HeatingUpPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HeatingUp extends BaseCard {
    public static final String ID = makeID(HeatingUp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public HeatingUp() {
        super(ID, info);

        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new HeatingUpPower(p, magicNumber), magicNumber));
    }
}
