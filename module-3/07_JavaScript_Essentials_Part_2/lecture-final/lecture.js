import axios from "axios";
import { Deck, Card} from './deck.js';

/***********************************************
 * Objects, constructor functions, and methods
 ***********************************************
 */
// Playing cards -- object literal
const queenOfHearts = {
  suit: "Hearts",
  rank: "Queen",
};

const kingOfClubs = {
  suit: "Clubs",
  rank: "King",
};
console.log(queenOfHearts);
console.log(kingOfClubs);

// Object prototype
// define the constructor
// function Card(suit, rank) {
//   this.suit = suit;
//   this.rank = rank;
// }

// create some cards from our constructor
const aceOfDiamonds = new Card("Diamonds", "Ace");
const jackOfHearts = new Card("Hearts", "Jack");
const tenOfSpades = new Card("Spades", 10);

// Card.prototype.toString = function () {
//   return `${this.rank} of ${this.suit}`;
// };

// Card.prototype.brand = "Standard Hoyle Deck";

console.log(aceOfDiamonds.toString());
console.log(tenOfSpades.brand);

const cardArray = [
  aceOfDiamonds,
  jackOfHearts,
  tenOfSpades,
  kingOfClubs,
  queenOfHearts,
];
for (let i = 0; i < cardArray.length; i++) {
  console.log(cardArray[i].toString());
}

// Deck object prototype constructor
// function Deck() {
//   // Create and array to hold the cards
//   this.cards = [];

//   // Define some suits as internal variable
//   const suits = ["Hearts", "Diamonds", "Clubs", "Spades"];
//   const ranks = ["Ace", 2, 3, 4, 5, 6, 7, 8, 9, 10, "Jack", "Queen", "King"];

//   // Let's create the cards and add them to the cards array
//   for (let suit of suits) {
//     for (let rank of ranks) {
//       this.cards.push(new Card(suit, rank));
//     }
//   }
// }

// Add shuffle method
// Deck.prototype.shuffle = function () {
//   for (let i = this.cards.length - 1; i > 0; i--) {
//     // get random spot
//     const j = Math.floor(Math.random() * (i + 1));
//     // JavaScript syntax for swapping items
//     [this.cards[i], this.cards[j]] = [this.cards[j], this.cards[i]];
//   }
// };

// Create a deck
const deck = new Deck();
// shuffle our deck
deck.shuffle();
console.log("===================================");
console.log("Shuffled deck: ");
console.log("");
for (let item of deck.cards) {
  console.log(item.toString());
}
/**********************************************
 * Destructuring assignment and spread syntax
 **********************************************
 */

console.log("==============================");
console.log("Destructuring and spread");
console.log("");
// Let's grab the array of cards
// This is the old boring way
const oldBoringCards = deck.cards;
// Using destructuring
const { cards } = deck;
// console.log(cards[0].toString());

// method to use the destructuring
function drawCard({ cards }) {
  return cards.pop();
}
const myCard = drawCard(deck);
console.log(myCard.toString());

// destructuring to get out of an array
const [firstCard] = cards;
console.log(firstCard.toString());

// example of skipping items
const [, secondCard, , fourthCard] = cards;
console.log(`Second Card: ${secondCard.toString()}`);
console.log(`Fourth Card: ${fourthCard.toString()}`);

// Lets make a Canasta deck
const secondDeck = new Deck();
const baseCards = secondDeck.cards;
// a deck with 104 cards (duplicates of standard deck), four jokers
console.log(cards.length);
const canastaDeck = [
  ...baseCards,
  ...baseCards,
  { suit: "Joker", rank: "Joker" },
  new Card("Joker", "Joker"),
  { suit: "Joker", rank: "Joker" },
  new Card("Joker", "Joker"),
];

console.log("==============================");
console.log("Canasta Deck");
console.log("");
console.log(`Length: ${canastaDeck.length}` )
for(let item of canastaDeck){
    console.log(`Card in Canasta Deck: ${item.toString()}`)
}

// Add color to a card
const cardWithColor = {...aceOfDiamonds, 
    color: aceOfDiamonds.suit === 'Hearts' || aceOfDiamonds.suit === 'Diamonds' ? 'red' : 'black'};
console.log(`Card color ${cardWithColor.color}`)
console.log(cardWithColor);

/*****************************************************
 * Functions, function expressions, and arrow syntax
 *****************************************************
 */

/**********************
 * Callback functions
 **********************
 */

/*******************************************
 * Array methods with synchronous callbacks
 *******************************************
 */

/********************************************
 * Asynchronous callbacks and API requests
 ********************************************
 */
