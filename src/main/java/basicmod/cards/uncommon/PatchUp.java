package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

//Turn a non-Burn card in hand into a Burn; the effect depends on the card's type.
public class PatchUp extends BaseCard {
    public static final String ID = makeID(PatchUp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    public PatchUp() {
        super(ID, info);

        setDamage(16);
        setBlock(8);
        setMagic(3);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(1, cardStrings.EXTENDED_DESCRIPTION[0], c -> !c.cardID.equals(Burn.ID), cards -> {
            for (AbstractCard c : cards) {
                //StSLib puts the chosen card back in hand after this callback, so swap it for a Burn in a queued action.
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        int i = p.hand.group.indexOf(c);
                        if (i >= 0) addToTop(new TransformCardInHandAction(i, new Burn()));
                        isDone = true;
                    }
                });
                switch (c.type) {
                    case ATTACK:
                        addToTop(new DamageRandomEnemyAction(new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
                        break;
                    case SKILL:
                        addToTop(new GainBlockAction(p, p, block));
                        break;
                    case POWER:
                        addToTop(new DrawCardAction(magicNumber));
                        break;
                    default: //Curse or Status
                        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                            if (mo.isDeadOrEscaped()) continue;
                            addToTop(new ApplyPowerAction(mo, p, new WeakPower(mo, 1, false), 1));
                            addToTop(new ApplyPowerAction(mo, p, new VulnerablePower(mo, 1, false), 1));
                        }
                }
            }
        }));
    }
}
