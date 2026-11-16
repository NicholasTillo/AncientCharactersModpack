package basicmod.cards.uncommon;

import basicmod.actions.ChantAction;
import basicmod.actions.GainHeatAction;
import basicmod.actions.GainOverHeat;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.ChantPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RecklessHeating extends BaseCard {
    public static final String ID = makeID(RecklessHeating.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );


    private static final int CHANT = 2;
    private static final int CHANT_UPG = 1;

    public RecklessHeating() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(CHANT, CHANT_UPG);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true,true));

        for (CardGroup pile : new CardGroup[]{p.drawPile, p.hand, p.discardPile}) {
            for (AbstractCard c : pile.group) {
                if (c.cardID.equals(Burn.ID)) {
                    addToTop(new GainHeatAction(10));
                }
            }
        }

    }

}
