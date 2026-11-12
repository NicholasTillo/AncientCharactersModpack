package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.Heat;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Inferno extends BaseCard {
    public static final String ID = makeID(Inferno.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );
    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int BONUS = 1; //Extra damage per HEAT_STEP Heat
    private static final int HEAT_STEP = 10;

    public Inferno() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(BONUS);
    }

    private int heatBonus() {
        AbstractPower heat = AbstractDungeon.player.getPower(Heat.POWER_ID); //null when the player has no Heat
        return heat == null ? 0 : heat.amount / HEAT_STEP * magicNumber;
    }

    //Same approach as the base game's Perfected Strike: the bonus joins the base damage while powers are applied.
    @Override
    public void applyPowers() {
        int realBase = baseDamage;
        baseDamage += heatBonus();
        super.applyPowers();
        baseDamage = realBase;
        isDamageModified = damage != baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realBase = baseDamage;
        baseDamage += heatBonus();
        super.calculateCardDamage(m);
        baseDamage = realBase;
        isDamageModified = damage != baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }
}
