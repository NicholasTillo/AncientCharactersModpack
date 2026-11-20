package basicmod.cards.rare;

import basicmod.cards.BaseCard;
import basicmod.cards.CustomTags;
import basicmod.character.CharFar;
import basicmod.powers.DebuffWaxWeakness;
import basicmod.powers.MotlingPower;
import basicmod.powers.WaxCoated;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Hit the target, and copy its wax debuffs (Wax Coated, Wax Weakness, Motling) onto every other enemy.
public class FullExorcism extends BaseCard {
    public static final String ID = makeID(FullExorcism.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            0
    );

    public FullExorcism() {
        super(ID, info);

        setDamage(4, 3);
        tags.add(CustomTags.WAX);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Amounts are read now, so they still spread if the hit kills the target.
        AbstractPower coated = m.getPower(WaxCoated.POWER_ID);
        AbstractPower weak = m.getPower(DebuffWaxWeakness.POWER_ID);
        AbstractPower motling = m.getPower(MotlingPower.POWER_ID);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (mo == m || mo.isDeadOrEscaped()) continue;
            if (coated != null && coated.amount > 0)
                addToBot(new ApplyPowerAction(mo, p, new WaxCoated(mo, coated.amount), coated.amount));
            if (weak != null && weak.amount > 0)
                addToBot(new ApplyPowerAction(mo, p, new DebuffWaxWeakness(mo, weak.amount), weak.amount));
            if (motling != null && motling.amount > 0)
                addToBot(new ApplyPowerAction(mo, p, new MotlingPower(mo, motling.amount), motling.amount));
        }
    }
}
