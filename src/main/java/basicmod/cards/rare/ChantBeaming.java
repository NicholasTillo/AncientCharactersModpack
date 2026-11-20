package basicmod.cards.rare;

import basicmod.cards.BaseCard;
import basicmod.cards.common.AbyssalPeering;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

//Hit a random enemy, then add a copy of the last Chant card played this combat to your hand.
public class ChantBeaming extends BaseCard {
    public static final String ID = makeID(ChantBeaming.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );

    public ChantBeaming() {
        super(ID, info);

        setDamage(8, 3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
        //Same "Chant card" test as Abyssal Peering. Chant Beamings are skipped so it can't copy itself.
        ArrayList<AbstractCard> played = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        for (int i = played.size() - 1; i >= 0; i--) {
            AbstractCard c = played.get(i);
            if (!c.cardID.equals(ID) && AbyssalPeering.isChantCard(c)) {
                addToBot(new MakeTempCardInHandAction(c.makeStatEquivalentCopy(), 1));
                break;
            }
        }
    }
}
