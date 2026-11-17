package basicmod.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

//Archetype tags. Check with card.hasTag(CustomTags.WAX).
public class CustomTags {
    @SpireEnum public static AbstractCard.CardTags WAX; //Waning Wax cards
    @SpireEnum public static AbstractCard.CardTags WAX_MAGIC; //This card's !M! is a wax amount, so Wax Mastery adds to it
}
