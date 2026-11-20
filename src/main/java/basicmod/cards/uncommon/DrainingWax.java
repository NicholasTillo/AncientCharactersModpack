package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.cards.CustomTags;
import basicmod.character.CharFar;
import basicmod.powers.DebuffWaxWeakness;
import basicmod.powers.MeltingStrengthPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Hit, gain Wax Strength, give the target Wax Weakness.
public class DrainingWax extends BaseCard {
    public static final String ID = makeID(DrainingWax.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public DrainingWax() {
        super(ID, info);

        setDamage(8, 3);
        setMagic(2);
        tags.add(CustomTags.WAX);
        tags.add(CustomTags.WAX_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(p, p, new MeltingStrengthPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(m, p, new DebuffWaxWeakness(m, magicNumber), magicNumber));
    }
}
