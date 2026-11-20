package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Hits once for each other card played this turn (like Finisher), and shows the hit count on the card.
public class FlurryOfGlart extends BaseCard {
    public static final String ID = makeID(FlurryOfGlart.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    public FlurryOfGlart() {
        super(ID, info);

        setDamage(4, 2);
    }

    private int hits() {
        int n = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn)
            if (c != this) n++;
        return n;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + hits() + cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int n = hits();
        for (int i = 0; i < n; i++)
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}
