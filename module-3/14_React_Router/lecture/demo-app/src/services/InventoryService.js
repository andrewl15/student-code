/**
 * This service class is used to mimic the data from an API.
 */

export default {
  /**
   * gets all the items in the inventory
   * @returns all the items in the inventory
   */
  getItems() {
    return items;
  },

  /**
   *
   * @param {number} id - the id of the item to fetch
   * @returns returns the item with the given id
   */
  getItem(id) {
    return items.find((item) => item.id == id);
  },

};

// This is a mock inventory of items
const items = [
  {
    id: 1,
    name: 'Phoenix Ember Pendant',
    description:
      'A glowing red-orange gemstone that radiates gentle warmth. When worn, it grants the wearer resistance to fire and allows them to rekindle small flames with a touch. Once per week, it can restore a person from unconsciousness with a surge of warmth.',
    itemType: 'Amulet',
    price: 350,
  },
  {
    id: 2,
    name: 'Whispering Quill',
    description:
      'A sleek black feathered quill that writes on its own when dictated to. It also has the ability to record and replay spoken words in a hushed, ghostly voice. Perfect for secret messages and studying in silence.',
    itemType: 'Writing Tool',
    price: 120,
  },
  {
    id: 3,
    name: 'Everflowing Potion Flask',
    description:
      'A small, ornate flask that magically refills with a random potion once per day. The liquid inside could be a healing elixir, a strength-enhancing brew, or even a potion of invisibility—though occasionally, it may produce something… unexpected.',
    itemType: 'Potion Container',
    price: 500,
  },
  {
    id: 4,
    name: 'Moonveil Cloak',
    description:
      'A shimmering silver cloak that grants the wearer enhanced stealth at night. Under the light of the full moon, it also provides brief invisibility (lasting one minute) when the wearer whispers a secret.',
    itemType: 'Clothing',
    price: 750,
  },
  {
    id: 5,
    name: 'Runestone of Echoing Strikes',
    description:
      "A palm-sized stone engraved with glowing runes. When held in the off-hand, it allows the wielder's next melee attack to strike twice in quick succession. The magic recharges at dawn.",
    itemType: 'Combat Enhancer',
    price: 600,
  },
  {
    id: 6,
    name: 'Glitter Pixie Dust',
    itemType: 'Combat Enhancer',
    description:
      "A tiny bag of glittery, sparkly pixie dust. When held in the off-hand, it allows the wielder's next melee attack to strike twice in quick succession. The magic recharges at dawn.",
    price: 2000,
  },
  {
    id: 7,
    name: 'Celestial Compass',
    description:
      'A beautifully crafted compass that always points towards the nearest source of magic. It can also reveal hidden paths and secret doors when held under the light of the stars.',
    itemType: 'Navigation Tool',
    price: 400,
  },
  {
    id: 8,
    name: 'Everbloom Seed',
    description:
      'A small, glowing seed that can sprout into a beautiful flower in any environment. The flower emits a calming aura, providing a +2 bonus to Wisdom saving throws for those within a 10-foot radius.',
    itemType: 'Plant',
    price: 300,
  },
  {
    id: 9,
    name: 'Spectral Lantern',
    description:
      'A lantern that casts a soft, ghostly light. It can illuminate invisible creatures and objects, revealing their outlines in the light. The lantern can also be used to communicate with spirits.',
    itemType: 'Light Source',
    price: 450,
  },
  {
    id: 10,
    name: 'Frostfire Blade',
    description:
      'A sword that shimmers with both icy blue and fiery red hues. It deals an extra 1d6 cold damage and 1d6 fire damage on a hit. Once per day, it can unleash a wave of frostfire, dealing area damage.',
    itemType: 'Weapon',
    price: 1200,
  },
];
