package basicmod.cards.common;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Stab extends BaseCard {
    public static final String ID = makeID(Stab.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );
    private static final int DAMAGE = 11;
    private static final int UPG_DAMAGE = 3;

    public Stab() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        cardsToPreview = new Burn();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new MakeTempCardInHandAction(new Burn(), 1));
    }
}
