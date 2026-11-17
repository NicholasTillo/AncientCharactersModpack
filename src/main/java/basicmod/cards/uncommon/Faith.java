package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.FaithPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Faith extends BaseCard {
    public static final String ID = makeID(Faith.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    private static final int BLOCK = 2;
    private static final int BLOCK_UPG = 1;

    public Faith() {
        super(ID, info);
        setMagic(BLOCK, BLOCK_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FaithPower(p, magicNumber), magicNumber));
    }
}
