package basicmod.cards.special;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//A card created by the Chant track.
public class Outburst extends BaseCard {
    public static final String ID = makeID(Outburst.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.NONE, //Each played card goes at a random enemy, like Havoc.
            0
    );
    private static final int CARDS = 2;
    private static final int CARDS_UPG = 1;

    public Outburst() {
        super(ID, info);

        setMagic(CARDS, CARDS_UPG);
        setExhaust(true);
        tags.add(ChantTier.CHANT_UNCOMMON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //The base game's Havoc action, without exhausting the played cards.
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
        }
    }
}
