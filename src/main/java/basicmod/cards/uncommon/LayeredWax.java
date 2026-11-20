package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.cards.CustomTags;
import basicmod.character.CharFar;
import basicmod.powers.MeltingBlockPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Gain Block now and Melting Block for later turns.
public class LayeredWax extends BaseCard {
    public static final String ID = makeID(LayeredWax.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public LayeredWax() {
        super(ID, info);

        setBlock(8, 3);
        setMagic(4, 1);
        tags.add(CustomTags.WAX);
        tags.add(CustomTags.WAX_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new MeltingBlockPower(p, magicNumber), magicNumber));
    }
}
