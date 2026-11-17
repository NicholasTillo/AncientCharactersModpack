package basicmod.cards.special;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Chant reward card.
public class Tarot extends BaseCard {
    public static final String ID = makeID(Tarot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.ENEMY,
            0
    );

    public Tarot() {
        super(ID, info);

        setDamage(20, 6);
        setExhaust(true);
        tags.add(ChantTier.CHANT_COMMON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }
}
