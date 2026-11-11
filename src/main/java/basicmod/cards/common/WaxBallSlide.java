package basicmod.cards.common;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

public class WaxBallSlide extends BaseCard {
    public static final String ID = makeID(WaxBallSlide.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 4;
    private static final int DROP = 1; //Damage lost per slide



    public WaxBallSlide() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(DROP);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Hit the target, then each living enemy to its right (wrapping to the left), for DROP less each time.
        List<AbstractMonster> mons = AbstractDungeon.getMonsters().monsters;
        for (int i = 0, hits = 0; i < mons.size(); i++) {
            AbstractMonster mo = mons.get((mons.indexOf(m) + i) % mons.size());
            if (mo.isDeadOrEscaped()) continue;
            DamageInfo dmg = new DamageInfo(p, baseDamage - magicNumber * hits++, DamageInfo.DamageType.NORMAL);
            dmg.applyPowers(p, mo); //Strength, Weak, and this enemy's Vulnerable
            addToBot(new DamageAction(mo, dmg, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

}
