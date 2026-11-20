# Disciples Of The Ancients

A Slay the Spire mod by Suspicious Chest Studios that adds a new playable character for each of the Ancients from
Slay the Spire 2.

**Status:** in development. The first character, The Apostle, is playable and in playtesting.

---

## The Apostle

A disciple of Tezcatara. 70 HP, 99 starting gold, and a starter relic, Smoldering Wick, that builds Heat at the end of
every turn and whenever you lose HP.

The Apostle plays around three original mechanics:

- **Heat:** builds toward your Max HP. At half, your attacks deal 1 more damage and you gain 1 Energy each turn. At
  full, you **Overheat**: draw 3 cards and gain 3 Energy and 3 Strength. Next turn you **Burnout**: lose the 3 Strength
  and 3 cards in your hand turn into Burns. Push your luck, or cool down.
- **Chant:** a progress track. Every 4 Chants, choose 1 of 3 tiered Chant cards to add to your hand.
- **Waxy:** short-lived wax buffs and debuffs (Wax Strength, Waxy Dexterity, Melting Block, Wax Coated and more) that
  melt away turn by turn. Waxy cards are Ethereal and Exhaust when played.

### Content so far

| | Count |
|---|---|
| Cards | 98 |
| Relics | 10 |
| Potions | 3 |
| Powers (status effects) | 31 |

---

## Coming next

One character for each of the remaining Ancients.

---

## Installing

1. Install [ModTheSpire](https://steamcommunity.com/sharedfiles/filedetails/?id=1605060445),
   [BaseMod](https://steamcommunity.com/sharedfiles/filedetails/?id=1605833019) and
   [StSLib](https://steamcommunity.com/sharedfiles/filedetails/?id=1609158507) from the Steam Workshop.
2. Build the mod with Maven (`mvn package`) and copy the jar from `target/` into your Slay the Spire `mods` folder.
3. Launch with ModTheSpire and enable Disciples Of The Ancients.

---

## Team

Suspicious Chest Studios: Nicholas Tillo, Raksha Rehal and Thijs Nusselder.

## Credits

Built on Alchyr's [BasicMod](https://github.com/Alchyr/BasicMod) template (MIT License), with HD template art from
Gremious's [DefaultMod](https://github.com/Gremious/StS-DefaultModBase).
