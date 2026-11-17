package basicmod.cards.common;

import basicmod.actions.GainHeatAction;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.Heat;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Stoke extends BaseCard {
    public static final String ID = makeID(Stoke.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );
    private static final int HEAT = 15;
    private static final int HEAT_UPG = 5;

    public Stoke() {
        super(ID, info);
        setMagic(HEAT, HEAT_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower heat = p.getPower(Heat.POWER_ID); //null when the player has no Heat
        boolean belowHalf = heat == null || ((Heat) heat).tier() < 1;
        addToBot(new GainHeatAction(belowHalf ? magicNumber * 2 : magicNumber));
    }
}
