import axios, { all } from "axios";
import { Deck, Card} from './deck.js';
import { response } from "express";

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
//declare a function to display cards
function logCards(arrayOfCards){
  for(let card of arrayOfCards){
    console.log(card);
  }
}
logCards(secondDeck.cards);
console.log('');

let displayCards = logCards;
console.log('=============================');
displayCards(secondDeck.cards);
console.log('=================== Display Cards ===========================');

displayCards = function(arrayOfCards){
  for(let card of arrayOfCards){
    console.log(`Card at index ${card} is ${card.toString()}`);
  }
}
displayCards(secondDeck.cards);

console.log('======================= displayCards arrow function ==================');
displayCards = arrayOfCards => {
  for(let card of arrayOfCards){
    console.log(`Arrow function ${card.toString()}`);
  }
}
displayCards(secondDeck.cards);
/**********************
 * Callback functions
 **********************
 */
console.log('=========================================');
function displayObjects(handOfCards, cardFormatter){
  for(const item of handOfCards){
    if(cardFormatter){
      console.log(cardFormatter(item));
    }else {
      console.log(item);
    }
  }
}
const firstFive = secondDeck.cards.splice(0,5);
displayObjects(firstFive, card => {return `From Callback ${card.toString()}`});
displayObjects(firstFive, fred => {return `${fred.rank}`});
displayCards(firstFive);

const pets = [
  {name: 'Eddie', species: 'German Shepard', age: 14},
  {name: 'Oreo', species: 'American Shorthair', age: 2}
]
/*******************************************
 * Array methods with synchronous callbacks
 *******************************************
 */
secondDeck.shuffle();
const dealtHand = secondDeck.cards.splice(0,5);
displayObjects(dealtHand);


const allDiamond = dealtHand.every(item => item.suit == 'Diamonds');
console.log(`Are they all diamonds? ${allDiamond}`);
const allHearts = firstFive.every(card => card.suit == 'Hearts');
console.log(`Is all hearts all hearts? ${allHearts}`);
const diamondsHands = dealtHand.filter(card => {return card.suit == 'Clubs'});
console.log(diamondsHands);


//find first card in hand that is less than a 5
const firstLessThan5 = dealtHand.filter(card => {return card.rank < 5});
console.log(firstLessThan5);



const ANSI_BLACK = '\u001B[30m';
const ANSI_RED = '\u001B[31m';

const handWIthPizza = dealtHand.map(item => {
  let color = item.suit == 'Hearts' || item.suit == 'Diamonds' ? ANSI_RED : ANSI_BLACK;
  return {...item, color}
})
handWIthPizza.forEach(item => {
  console.log(`${item.color} ${item.rank} of ${item.suit}`);
})


function getCardValue(card){
  switch(card.rank) {
    case 'Jack':
      return 11;
    case 'Queen':
      return 12;
    case 'King':
      return 13;
    case 'Ace':
      return 1;
    default:
      return parseInt(card.rank);
    
  }
}

//recue to get total value off the hand
const valueOfHand = dealtHand.reduce((total,card) =>{
  return total += getCardValue(card);
},0)
console.log(valueOfHand);

const numbers = [2,4,6,8];
const sumOfNumbers = numbers.reduce((sum, num) =>{
  return sum += num;
})
console.log(sumOfNumbers);

const freshDeck = new Deck();
const upperCards = freshDeck.cards.filter(card => [9,10,'Jack','Queen','King','Ace'].includes(card.rank));
console.log(`Length of upperCards ${upperCards.length}`);
const pinochleCards = [...upperCards,...upperCards];
console.log(`Length of pinochle deck: ${pinochleCards.length}`);
/********************************************
 * Asynchronous callbacks and API requests
 ********************************************
 */
//build a version of axios to use
let newDeck = [];
const client = axios.create();
function getGameDeck(){
  client.get('http://localhost:3000/deck')
    .then((response) => {
      console.log(response);
      displayObjects(response.data.deck.cards, item => {return `${item.rank} ${item.suit}`});
      newDeck = response.data.deck.cards;
      console.log(`The new deck of cards inside the then: ${newDeck.length}`);
    })
    .catch((error) => {
      if(error.response){ //talk to server error
        console.log(`Got back an error ${error.response.status}`)
      }else if(error.request){
        console.log(`I cannot talk to the server`);
      }
    })
}

getGameDeck();
console.log('After calling getGameDeck');