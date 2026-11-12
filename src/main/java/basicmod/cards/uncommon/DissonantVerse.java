package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.ChantPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DissonantVerse extends BaseCard {
    public static final String ID = makeID(DissonantVerse.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 6;

    public DissonantVerse() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Empty the Chant track. The power itself stays, so the count of completed Chants is kept.
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractPower chant = p.getPower(ChantPower.POWER_ID);
                if (chant != null) {
                    chant.amount = 0;
                    chant.flash();
                    chant.updateDescription();
                }
                isDone = true;
            }
        });
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }
}
