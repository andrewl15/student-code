function Card(suit, rank) {
  this.suit = suit;
  this.rank = rank;
}

Card.prototype.toString = function () {
  return `${this.rank} of ${this.suit}`;
};

Card.prototype.brand = "Standard Hoyle Deck";

function Deck() {
  // Create and array to hold the cards
  this.cards = [];

  // Define some suits as internal variable
  const suits = ["Hearts", "Diamonds", "Clubs", "Spades"];
  const ranks = ["Ace", 2, 3, 4, 5, 6, 7, 8, 9, 10, "Jack", "Queen", "King"];

  // Let's create the cards and add them to the cards array
  for (let suit of suits) {
    for (let rank of ranks) {
      this.cards.push(new Card(suit, rank));
    }
  }
}

Deck.prototype.shuffle = function () {
  for (let i = this.cards.length - 1; i > 0; i--) {
    // get random spot
    const j = Math.floor(Math.random() * (i + 1));
    // JavaScript syntax for swapping items
    [this.cards[i], this.cards[j]] = [this.cards[j], this.cards[i]];
  }
};

export {Card, Deck};
