package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.LetHimCookPower;
import basicmod.powers.WaxMastery;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheWaxMaster extends BaseCard {
    public static final String ID = makeID(TheWaxMaster.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int AMOUNT = 1;
    private static final int AMOUNT_UPG = 1;

    public TheWaxMaster() {
        super(ID, info);
        setMagic(AMOUNT , AMOUNT_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WaxMastery(p, magicNumber), magicNumber));
    }
}
