package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.cards.CustomTags;
import basicmod.character.CharFar;
import basicmod.powers.*;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.function.IntFunction;

//Double every wax power on you and on the target.
public class BuyBetterWax extends BaseCard {
    public static final String ID = makeID(BuyBetterWax.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public BuyBetterWax() {
        super(ID, info);

        setExhaust(true);
        setCostUpgrade(0);
        tags.add(CustomTags.WAX);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dbl(p, p, MeltingBlockPower.POWER_ID, a -> new MeltingBlockPower(p, a));
        dbl(p, p, MeltingStrengthPower.POWER_ID, a -> new MeltingStrengthPower(p, a));
        dbl(p, p, WaxyDexterity.POWER_ID, a -> new WaxyDexterity(p, a));
        dbl(p, p, WaxyDraws.POWER_ID, a -> new WaxyDraws(p, a));
        dbl(p, m, DebuffWaxWeakness.POWER_ID, a -> new DebuffWaxWeakness(m, a));
        dbl(p, m, WaxCoated.POWER_ID, a -> new WaxCoated(m, a));
    }

    //Doubling = applying the current amount again.
    private void dbl(AbstractPlayer p, AbstractCreature target, String id, IntFunction<AbstractPower> make) {
        AbstractPower pow = target.getPower(id);
        if (pow != null && pow.amount > 0)
            addToBot(new ApplyPowerAction(target, p, make.apply(pow.amount), pow.amount));
    }
}
