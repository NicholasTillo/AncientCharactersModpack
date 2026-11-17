package basicmod.cards.uncommon;

import basicmod.actions.LoseHeatAction;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SplashWax extends BaseCard {
    public static final String ID = makeID(SplashWax.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );
    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int BURN_BONUS = 4; //Extra damage if the exhausted card was a Burn
    private static final int HEAT_LOSS = 10;

    public SplashWax() {
        super(ID, info);
        tags.add(basicmod.cards.CustomTags.WAX);
        tags.add(basicmod.cards.CustomTags.WAX_MAGIC);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(BURN_BONUS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        //StSLib's hand picker. The callback's actions go on top, in reverse: the exhaust runs first, then the bonus hit.
        addToBot(new SelectCardsInHandAction(1, ExhaustAction.TEXT[0], cards -> {
            for (AbstractCard c : cards) {
                if (c.cardID.equals(Burn.ID)) {
                    DamageInfo bonus = new DamageInfo(p, magicNumber, DamageInfo.DamageType.NORMAL);
                    bonus.applyPowers(p, m); //Strength, Weak, and the enemy's Vulnerable
                    addToTop(new DamageAction(m, bonus, AbstractGameAction.AttackEffect.FIRE));
                }
                addToTop(new ExhaustSpecificCardAction(c, p.hand));
            }
        }));
        addToBot(new LoseHeatAction(HEAT_LOSS));
    }
}
