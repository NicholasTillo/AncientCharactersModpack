package basicmod.cards.uncommon;

import basicmod.cards.BaseCard;
import basicmod.cards.CustomTags;
import basicmod.character.CharFar;
import basicmod.powers.MeltingStrengthPower;
import basicmod.powers.WaxyDexterity;
import basicmod.powers.WaxyDraws;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Hit, then gain 1 of a random Wax buff (Waxy Draws, Waxy Dexterity or Wax Strength), magicNumber times.
public class LetsGoGambling extends BaseCard {
    public static final String ID = makeID(LetsGoGambling.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    public LetsGoGambling() {
        super(ID, info);

        setDamage(14, 4);
        setMagic(3);
        tags.add(CustomTags.WAX);
        tags.add(CustomTags.WAX_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        for (int i = 0; i < magicNumber; i++) {
            AbstractPower buff;
            switch (AbstractDungeon.cardRandomRng.random(2)) {
                case 0: buff = new WaxyDraws(p, 1); break;
                case 1: buff = new WaxyDexterity(p, 1); break;
                default: buff = new MeltingStrengthPower(p, 1);
            }
            addToBot(new ApplyPowerAction(p, p, buff, 1));
        }
    }
}
