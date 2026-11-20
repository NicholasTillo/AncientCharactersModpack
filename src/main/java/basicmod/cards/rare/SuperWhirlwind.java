package basicmod.cards.rare;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

//X cost: deal X damage to ALL enemies 8 times (11 upgraded). Strength etc. add to every hit.
public class SuperWhirlwind extends BaseCard {
    public static final String ID = makeID(SuperWhirlwind.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            -1 //X
    );

    public SuperWhirlwind() {
        super(ID, info);

        setMagic(8, 3); //number of hits
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = energyOnUse;
        if (p.hasRelic(ChemicalX.ID)) {
            x += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        if (x > 0) {
            baseDamage = x;
            calculateCardDamage(null); //fills multiDamage with X plus Strength etc. for each enemy
            for (int i = 0; i < magicNumber; i++)
                addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }
        //X cards spend the energy themselves.
        if (!freeToPlayOnce) p.energy.use(EnergyPanel.totalCount);
    }
}
