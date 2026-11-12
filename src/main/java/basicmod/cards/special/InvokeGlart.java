package basicmod.cards.special;

import basemod.BaseMod;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//A card created by the Chant track.
public class InvokeGlart extends BaseCard {
    public static final String ID = makeID(InvokeGlart.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.SELF,
            0
    );

    public InvokeGlart() {
        super(ID, info);

        setExhaust(true);
        tags.add(ChantTier.CHANT_UNCOMMON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //The base game's Expertise action: draw until the hand holds this many cards.
        addToBot(new ExpertiseAction(p, BaseMod.MAX_HAND_SIZE));
    }
}
