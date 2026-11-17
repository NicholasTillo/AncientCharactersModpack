package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.cards.CustomTags;
import basicmod.character.CharFar;
import basicmod.powers.SelfPortraitPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SelfPortrait extends BaseCard {
    public static final String ID = makeID(SelfPortrait.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public SelfPortrait() {
        super(ID, info);

        tags.add(CustomTags.WAX);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SelfPortraitPower(p, 1), 1));
    }
}
