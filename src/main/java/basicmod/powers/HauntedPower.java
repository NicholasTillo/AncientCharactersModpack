package basicmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basicmod.BasicMod.makeID;

//The next amount cards you play are played twice. When it runs out, Burnout.
public class HauntedPower extends BasePower {
    public static final String POWER_ID = makeID(HauntedPower.class.getSimpleName());

    public HauntedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }

    //Same replay as the base game's Burst / Double Tap.
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.purgeOnUse || amount <= 0) return; //the copy itself doesn't trigger another copy
        flash();
        AbstractMonster m = action.target instanceof AbstractMonster ? (AbstractMonster) action.target : null;
        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = card.current_x;
        tmp.target_y = card.current_y;
        if (m != null) tmp.calculateCardDamage(m);
        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    //ReducePowerAction removes the power at 0, which lands here.
    @Override
    public void onRemove() {
        addToBot(new ApplyPowerAction(owner, owner, new Burnout(owner)));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
