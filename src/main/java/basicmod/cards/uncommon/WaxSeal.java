package basicmod.cards.uncommon;

import basicmod.actions.LoseHeatAction;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.DebuffWaxWeakness;
import basicmod.powers.Heat;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WaxSeal extends BaseCard {
    public static final String ID = makeID(WaxSeal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    public WaxSeal() {
        super(ID, info);
        setMagic(4,1);
        tags.add(basicmod.cards.CustomTags.WAX);
        tags.add(basicmod.cards.CustomTags.WAX_MAGIC); //Wax Mastery adds to the Wax Weakness amount
        setCostUpgrade(1);
    }

    @Override
    public void onMoveToDiscard() {
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //damage was just worked out by calculateCardDamage, so Strength, Weak and Vulnerable are already in it.
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new DebuffWaxWeakness(mo, magicNumber)));
            }
        }
    }
}
