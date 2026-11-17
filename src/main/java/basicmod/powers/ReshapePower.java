package basicmod.powers;

import basemod.abstracts.cardbuilder.actionbuilder.EffectActionBuilder;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basicmod.BasicMod.makeID;

//At the end of combat, heal if the owner Overheated at any point this combat (same shape as the base game's Self Repair).
public class ReshapePower extends BasePower {
    public static final String POWER_ID = makeID("ReshapePower");

    public ReshapePower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
    }


    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        if(card.cardID.equals(Burn.ID)) {
            flash();
            addToBot(new DamageRandomEnemyAction(new DamageInfo(owner, amount), AbstractGameAction.AttackEffect.FIRE));

        }
    }


}
