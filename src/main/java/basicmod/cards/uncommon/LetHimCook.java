package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.LetHimCookPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LetHimCook extends BaseCard {
    public static final String ID = makeID(LetHimCook.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    private static final int HEAL = 10;
    private static final int HEAL_UPG = 5;

    public LetHimCook() {
        super(ID, info);
        setMagic(HEAL, HEAL_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new LetHimCookPower(p, magicNumber), magicNumber));
    }
}
