package basicmod.cards.uncommon;

import basicmod.actions.LoseHeatAction;
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

public class Vent extends BaseCard {
    public static final String ID = makeID(Vent.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    public Vent() {
        super(ID, info);
        setDamage(0);
        setCostUpgrade(1);
    }

    //Half of your Heat is lost when this is played.
    private static int heatLost() {
        AbstractPower heat = AbstractDungeon.player.getPower(Heat.POWER_ID); //null when the player has no Heat
        return heat == null ? 0 : heat.amount / 2;
    }

    //Same approach as the base game's Body Slam: the base damage follows your Heat, and the
    //"(Deals N damage.)" line is added to the text while the card is in hand.
    private void showDamage() {
        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        baseDamage = heatLost() / 2;
        super.applyPowers();
        showDamage();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        baseDamage = heatLost() / 2;
        super.calculateCardDamage(m);
        showDamage();
    }

    @Override
    public void onMoveToDiscard() {
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //damage was just worked out by calculateCardDamage, so Strength, Weak and Vulnerable are already in it.
        addToBot(new LoseHeatAction(heatLost()));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }
}
