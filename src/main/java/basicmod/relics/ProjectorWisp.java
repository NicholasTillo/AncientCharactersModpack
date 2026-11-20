package basicmod.relics;

import basicmod.character.CharFar;
import basicmod.powers.SelfPortraitPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.Comparator;

import static basicmod.BasicMod.makeID;

//Whenever you Overheat, add a random Waxy Apostle or Colorless Attack to your hand. It costs 0 this turn. Triggered from Overheat.
public class ProjectorWisp extends BaseRelic {
    public static final String ID = makeID(ProjectorWisp.class.getSimpleName());

    public ProjectorWisp() {
        super(ID, ProjectorWisp.class.getSimpleName(), CharFar.Meta.CARD_COLOR, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    //A random Apostle or Colorless Attack (common/uncommon/rare only), made Waxy and free this turn. Null if there are none.
    public static AbstractCard randomWaxyAttack() {
        ArrayList<AbstractCard> pool = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.type != AbstractCard.CardType.ATTACK || c.hasTag(AbstractCard.CardTags.HEALING)) continue;
            if (c.color != CharFar.Meta.CARD_COLOR && c.color != AbstractCard.CardColor.COLORLESS) continue;
            if (c.rarity == AbstractCard.CardRarity.COMMON || c.rarity == AbstractCard.CardRarity.UNCOMMON || c.rarity == AbstractCard.CardRarity.RARE)
                pool.add(c);
        }
        if (pool.isEmpty()) return null;
        pool.sort(Comparator.comparing(c -> c.cardID)); //same seed, same result
        AbstractCard card = SelfPortraitPower.makeWaxy(pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1)).makeCopy());
        card.setCostForTurn(0);
        return card;
    }
}
