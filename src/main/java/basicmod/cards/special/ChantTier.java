package basicmod.cards.special;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

//Tags marking how rare a Chant-generated card is. The cards stay CardRarity.SPECIAL so they never show up in rewards or shops.
//Mark a card with: tags.add(ChantTier.CHANT_COMMON);   Check one with: card.hasTag(ChantTier.CHANT_COMMON)
public class ChantTier {
    @SpireEnum public static AbstractCard.CardTags CHANT_COMMON;
    @SpireEnum public static AbstractCard.CardTags CHANT_UNCOMMON;
    @SpireEnum public static AbstractCard.CardTags CHANT_RARE;
}
