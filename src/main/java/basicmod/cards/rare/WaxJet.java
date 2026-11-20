package basicmod.cards.rare;

import basicmod.cards.BaseCard;
import basicmod.cards.CustomTags;
import basicmod.character.CharFar;
import basicmod.powers.*;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Hit ALL enemies, then remove every Wax buff and debuff on you; ALL enemies get Wax Coated per stack removed.
public class WaxJet extends BaseCard {
    public static final String ID = makeID(WaxJet.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );
    private static final String[] WAX_POWERS = {
            MeltingBlockPower.POWER_ID, MeltingStrengthPower.POWER_ID, WaxyDexterity.POWER_ID, WaxyDraws.POWER_ID,
            DebuffWaxWeakness.POWER_ID, WaxFrail.POWER_ID, WaxCoated.POWER_ID
    };

    public WaxJet() {
        super(ID, info);

        setDamage(12, 4);
        setMagic(4, 1);
        isMultiDamage = true;
        tags.add(CustomTags.WAX);
        tags.add(CustomTags.WAX_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //The damage is worked out before the Wax is removed, so Wax Strength still adds to this hit.
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        int stacks = 0;
        for (String id : WAX_POWERS) {
            AbstractPower pow = p.getPower(id);
            if (pow != null && pow.amount > 0) {
                stacks += pow.amount;
                addToBot(new RemoveSpecificPowerAction(p, p, pow));
            }
        }
        int coated = stacks * magicNumber;
        if (coated <= 0) return;
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped())
                addToBot(new ApplyPowerAction(mo, p, new WaxCoated(mo, coated), coated));
        }
    }
}
