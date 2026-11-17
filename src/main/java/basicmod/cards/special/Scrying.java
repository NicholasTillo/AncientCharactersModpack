package basicmod.cards.special;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//A card created by the Chant track.
public class Scrying extends BaseCard {
    public static final String ID = makeID(Scrying.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.SELF,
            0
    );
    private static final int SCRY = 3;
    private static final int SCRY_UPG = 2;

    public Scrying() {
        super(ID, info);

        setMagic(SCRY, SCRY_UPG);
        setExhaust(true);
        tags.add(ChantTier.CHANT_COMMON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScryAction(magicNumber));
        addToBot(new DrawCardAction(1));
    }
}
