package basicmod.cards.common;

import basicmod.actions.ChantAction;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.ChantPower;
import basicmod.powers.Heat;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SturdyBody extends BaseCard {
    public static final String ID = makeID(SturdyBody.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int BLOCK = 5;
    private static final int BLOCK_UPG = 2;
    private static final int MAGIC = 5;
    private static final int MAGIC_UPG = 2;


    public SturdyBody() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setBlock(BLOCK, BLOCK_UPG);
        setMagic(MAGIC, MAGIC_UPG);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower heat = p.getPower(Heat.POWER_ID); //null when the player has no Heat
        boolean hot = heat != null && ((Heat) heat).tier() >= 1;
        addToBot(new GainBlockAction(p, p, hot ? block + magicNumber : block));
    }

}
