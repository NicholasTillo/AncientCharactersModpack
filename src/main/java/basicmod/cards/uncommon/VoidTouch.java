package basicmod.cards.uncommon;

import basicmod.actions.ChantAction;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Chant 4 times, shuffle a Void into your draw pile.
public class VoidTouch extends BaseCard {
    public static final String ID = makeID(VoidTouch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public VoidTouch() {
        super(ID, info);

        setMagic(4);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) addToBot(new ChantAction(1));
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
    }
}
