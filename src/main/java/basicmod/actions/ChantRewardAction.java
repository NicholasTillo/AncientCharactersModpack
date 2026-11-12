package basicmod.actions;

import basicmod.cards.special.ChantTier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

import java.util.ArrayList;
import java.util.Comparator;

//The reward for completing a Chant: pick 1 of up to 3 Chant cards (same screen as Discovery).
//Chant 1 offers commons, chant 2 uncommons, chant 3 rares. From chant 4 on the cards are upgraded and
//the tier of each is rolled, with the higher tiers getting more likely every chant.
public class ChantRewardAction extends AbstractGameAction {
    private static final int CHOICES = 3;
    private final int chantNumber; //1 for the first Chant completed this combat, 2 for the second...
    private boolean retrieveCard = false;

    public ChantRewardAction(int chantNumber) {
        this.chantNumber = chantNumber;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    //Relative chance of {common, uncommon, rare} for this chant.
    static int[] tierWeights(int chantNumber) {
        if (chantNumber == 1) return new int[]{1, 0, 0};
        if (chantNumber == 2) return new int[]{0, 1, 0};
        if (chantNumber == 3) return new int[]{0, 0, 1};
        int extra = chantNumber - 3; //chant 4 is 4:3:1, chant 5 is 4:4:2, chant 6 is 4:5:3...
        return new int[]{4, 2 + extra, extra};
    }

    private ArrayList<AbstractCard> generateChoices() {
        AbstractCard.CardTags[] tiers = {ChantTier.CHANT_COMMON, ChantTier.CHANT_UNCOMMON, ChantTier.CHANT_RARE};
        ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<>();
        for (AbstractCard.CardTags tier : tiers) {
            ArrayList<AbstractCard> pool = new ArrayList<>();
            for (AbstractCard c : CardLibrary.getAllCards()) {
                if (c.hasTag(tier)) pool.add(c);
            }
            pool.sort(Comparator.comparing(c -> c.cardID)); //fixed order, so the same seed gives the same choices
            pools.add(pool);
        }

        int[] weights = tierWeights(chantNumber);
        ArrayList<AbstractCard> choices = new ArrayList<>();
        while (choices.size() < CHOICES) {
            //Only tiers that still have a card left can be rolled.
            int total = 0;
            for (int i = 0; i < tiers.length; i++) {
                if (!pools.get(i).isEmpty()) total += weights[i];
            }
            if (total == 0) break; //ran out of cards: offer fewer choices

            int roll = AbstractDungeon.cardRandomRng.random(total - 1);
            for (int i = 0; i < tiers.length; i++) {
                if (pools.get(i).isEmpty()) continue;
                roll -= weights[i];
                if (roll < 0) {
                    ArrayList<AbstractCard> pool = pools.get(i);
                    AbstractCard c = pool.remove(AbstractDungeon.cardRandomRng.random(pool.size() - 1)).makeCopy();
                    if (chantNumber > 3) c.upgrade();
                    choices.add(c);
                    break;
                }
            }
        }
        return choices;
    }

    //Same flow as the base game's DiscoveryAction.
    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            ArrayList<AbstractCard> choices = generateChoices();
            if (choices.isEmpty()) {
                isDone = true;
                return;
            }
            AbstractDungeon.cardRewardScreen.customCombatOpen(choices, CardRewardScreen.TEXT[1], false);
            tickDuration();
            return;
        }
        if (!retrieveCard) {
            AbstractCard picked = AbstractDungeon.cardRewardScreen.discoveryCard;
            if (picked != null) {
                addToTop(new MakeTempCardInHandAction(picked.makeStatEquivalentCopy()));
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            retrieveCard = true;
        }
        tickDuration();
    }
}
