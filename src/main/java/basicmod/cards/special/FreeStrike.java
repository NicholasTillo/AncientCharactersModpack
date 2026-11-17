package basicmod.cards.special;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//The card created by the Chant track. The effect below is a placeholder.
public class FreeStrike extends BaseCard {
    public static final String ID = makeID(FreeStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL, //SPECIAL keeps it out of card rewards and shops.
            CardTarget.ALL_ENEMY,
            0
    );
    private static final int DMG = 13;
    private static final int DMG_UPG = 13;

    public FreeStrike() {
        super(ID, info);

        setDamage(DMG, DMG_UPG);
        setExhaust(true);
        tags.add(ChantTier.CHANT_UNCOMMON);
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p,multiDamage,DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, false));
    }
}
