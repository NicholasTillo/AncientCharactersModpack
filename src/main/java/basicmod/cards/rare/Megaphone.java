package basicmod.cards.rare;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.MegaphonePower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Megaphone extends BaseCard {
    public static final String ID = makeID(Megaphone.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public Megaphone() {
        super(ID, info);

        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MegaphonePower(p, 1), 1));
    }
}
