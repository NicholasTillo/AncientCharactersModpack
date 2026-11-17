package basicmod.cards.common;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.ChantPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ProtectiveChant extends BaseCard {
    public static final String ID = makeID(ProtectiveChant.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );
    private static final int BLOCK = 8;
    private static final int BLOCK_UPG = 3;
    private static final int BONUS = 4; //Extra Block if a Chant was completed this turn

    public ProtectiveChant() {
        super(ID, info);
        setBlock(BLOCK, BLOCK_UPG);
        setMagic(BONUS);
    }

    private static boolean chantedThisTurn() {
        AbstractPower chant = AbstractDungeon.player.getPower(ChantPower.POWER_ID); //null when the player has never chanted
        return chant != null && ((ChantPower) chant).completedThisTurn > 0;
    }

    //The bonus is added to the base Block, so Dexterity and Frail apply to the total.
    @Override
    protected void applyPowersToBlock() {
        int realBase = baseBlock;
        if (chantedThisTurn()) baseBlock += magicNumber;
        super.applyPowersToBlock();
        baseBlock = realBase;
        isBlockModified = block != baseBlock;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }
}
