package basicmod.cards.common;

import basicmod.actions.GainWaxStrength;
import basicmod.actions.GainWaxyDex;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.WaxyDraws;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

public class SmokeSignals extends BaseCard {
    public static final String ID = makeID(SmokeSignals.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DRAW_UP = 2;
    private static final int DRAW_UP_UPG = 1;



    public SmokeSignals() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        tags.add(basicmod.cards.CustomTags.WAX);
        tags.add(basicmod.cards.CustomTags.WAX_MAGIC);
        setMagic(DRAW_UP, DRAW_UP_UPG);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new WaxyDraws(p,magicNumber)));
    }
}


