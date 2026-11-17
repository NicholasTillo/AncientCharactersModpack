package basicmod.cards.special;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import basicmod.actions.MeltingBlock;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Chant reward card.
public class Ipsum extends BaseCard {
    public static final String ID = makeID(Ipsum.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.SELF,
            0
    );

    public Ipsum() {
        super(ID, info);

        setBlock(8, 4);
        setMagic(1);
        setExhaust(true);
        tags.add(ChantTier.CHANT_UNCOMMON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, magicNumber)));
        addToBot(new MeltingBlock(block));
    }
}
