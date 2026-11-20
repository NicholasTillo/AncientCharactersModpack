package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.cards.CustomTags;
import basicmod.character.CharFar;
import basicmod.powers.MotlingPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Crystal formations in candles.
public class Motling extends BaseCard {
    public static final String ID = makeID(Motling.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public Motling() {
        super(ID, info);

        setMagic(8, 3);
        tags.add(CustomTags.WAX);
        tags.add(CustomTags.WAX_MAGIC); //Wax Mastery adds to the damage
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new MotlingPower(m, magicNumber), magicNumber));
    }
}
