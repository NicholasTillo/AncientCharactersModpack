package basicmod.cards.uncommon;

import basemod.BaseMod;
import basicmod.actions.ChantAction;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Unearth extends BaseCard {
    public static final String ID = makeID(Unearth.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );


    private static final int CHANT = 1;
    private static final int CHANT_UPG = 1;

    public Unearth() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(CHANT, CHANT_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("Burnout")) {
            addToBot(new SelectCardsAction(p.discardPile.group, 1, "Choose a card to put into your hand.", cards -> {
                for (AbstractCard c : cards) {
                    if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) break;
                    c.setCostForTurn(0);
                    p.discardPile.moveToHand(c, p.discardPile);
                }
            }));

        }

    }
}
