package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Flat damage to ALL enemies: ignores Block, Strength, Weak, Wax Weakness, Vulnerable, everything.
//Uses magicNumber instead of damage so the card's number never changes, and HP_LOSS so Block and powers don't touch it.
public class FamiliarBeam extends BaseCard {
    public static final String ID = makeID(FamiliarBeam.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    public FamiliarBeam() {
        super(ID, info);

        setMagic(8, 3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(magicNumber, true),
                DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.FIRE));
    }
}
