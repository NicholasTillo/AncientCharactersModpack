package basicmod.cards.rare;

import basicmod.cards.BaseCard;
import basicmod.character.CharFar;
import basicmod.powers.ClonePower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//Lose HP, summon a Clone with half your Max HP that soaks attack damage before you.
public class Clone extends BaseCard {
    public static final String ID = makeID(Clone.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CharFar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Clone() {
        super(ID, info);

        setMagic(8); //HP lost
        setExhaust(true);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //HP loss isn't attack damage, so it always hits you, never the Clone.
        addToBot(new LoseHPAction(p, p, magicNumber));
        int hp = p.maxHealth / 2;
        addToBot(new ApplyPowerAction(p, p, new ClonePower(p, hp), hp));
    }
}
