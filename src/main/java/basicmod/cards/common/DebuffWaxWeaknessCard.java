package basicmod.cards.common;

import basicmod.actions.ApplyDebuffWaxWeakness;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DebuffWaxWeaknessCard extends BaseCard {
    public static final String ID = makeID(DebuffWaxWeaknessCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );



    public DebuffWaxWeaknessCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        tags.add(basicmod.cards.CustomTags.WAX);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = energyOnUse;                       // energy you had when the card was played
        if (p.hasRelic(ChemicalX.ID)) {
            x += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        x += basicmod.powers.WaxMastery.bonus();
        addToBot(new ApplyDebuffWaxWeakness(m, x));

        if (!freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }
}


