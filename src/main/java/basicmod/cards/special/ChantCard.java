package basicmod.cards.special;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//The card created by the Chant track. The effect below is a placeholder.
public class ChantCard extends BaseCard {
    public static final String ID = makeID(ChantCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.SELF,
            0
    );
    private static final int BLOCK = 5;
    private static final int BLOCK_UPG = 3;

    public ChantCard() {
        super(ID, info);

        setBlock(BLOCK, BLOCK_UPG);
        setExhaust(true);
        tags.add(ChantTier.CHANT_COMMON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }
}
