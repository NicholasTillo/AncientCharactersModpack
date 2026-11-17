package basicmod.cards.common;

import basicmod.actions.ChantAction;
import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.ChantPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WaxingMoon extends BaseCard {
    public static final String ID = makeID(WaxingMoon.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );
    private static final int BLOCK =14;
    private static final int BLOCK_UPG = 4;

    public WaxingMoon() {
        super(ID, info);
        setBlock(BLOCK, BLOCK_UPG);
        setEthereal(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ChantAction(2));


    }
}
