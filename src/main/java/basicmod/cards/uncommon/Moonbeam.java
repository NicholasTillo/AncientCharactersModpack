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

//Costs 1 less for each Skill played this turn.
public class Moonbeam extends BaseCard {
    public static final String ID = makeID(Moonbeam.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            3
    );

    public Moonbeam() {
        super(ID, info);

        setDamage(21, 7);
    }

    //Recount from the turn's played cards, so the discount resets by itself each turn.
    private void refreshCost() {
        if (AbstractDungeon.player == null) return; //compendium
        int skills = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn)
            if (c.type == CardType.SKILL) skills++;
        setCostForTurn(cost - skills); //clamps at 0
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        refreshCost();
    }

    @Override
    public void triggerWhenDrawn() {
        refreshCost();
    }

    @Override
    public void atTurnStart() {
        refreshCost();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }
}
