package basicmod.cards.rare;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.DebuffWaxWeakness;
import basicmod.powers.WaxFrail;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Big hit with a drawback: you get Wax Weakness and Wax Frail.
public class Halloween extends BaseCard {
    public static final String ID = makeID(Halloween.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    public Halloween() {
        super(ID, info);

        setDamage(40, 10);
        setMagic(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new DebuffWaxWeakness(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new WaxFrail(p, magicNumber), magicNumber));
    }
}
