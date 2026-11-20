package basicmod.cards.uncommon;

import basicmod.actions.LoseHeatAction;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.TemporaryThorns;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

//Gain Block and Thorns until your next turn, lose 10 Heat.
public class FormShell extends BaseCard {
    public static final String ID = makeID(FormShell.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    private static final int HEAT_LOSS = 10;

    public FormShell() {
        super(ID, info);

        setBlock(8, 3);
        setMagic(3, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new TemporaryThorns(p, magicNumber), magicNumber));
        addToBot(new LoseHeatAction(HEAT_LOSS));
    }
}
