package basicmod.cards.special;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

//The card created by the Chant track. The effect below is a placeholder.
public class ChainsOfBinding extends BaseCard {
    public static final String ID = makeID(ChainsOfBinding.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.ENEMY,
            0
    );
    private static final int DEBUFF = 4;
    private static final int DEBUFF_UPG = 95;

    public ChainsOfBinding() {
        super(ID, info);

        setMagic(4, 95);
        setExhaust(true);
        tags.add(ChantTier.CHANT_UNCOMMON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, m, new WeakPower(p,magicNumber,false)));
    }
}
