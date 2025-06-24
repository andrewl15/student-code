import axios from 'axios';
import { Deck, Card } from './deck';

/***********************************************
 * Objects, constructor functions, and methods
 ***********************************************
 */
const queenOfHearts = {
    suit: 'Hearts',
    rank: 'Queen'
}

const kingOfClubs = {
    suit: 'Clubs',
    rank: 'King'
}
console.log(queenOfHearts);
console.log(kingOfClubs);


console.log(aceOfDiamonds.toString());
const cardArray = [aceOfDiamonds, jackOfHearts];
for(const card of cardArray){
    console.log(card);
}




const deck = new Deck();
 deck.shuffle();
 console.log('_________________________')
 console.log('Shuffled deck:')
 for(let item of deck.cards){
    console.log(item.toString());
 }
/**********************************************
 * Destructuring assignment and spread syntax
 **********************************************
 */
const oldBoringCards = deck.cards;

const { cards } = deck;

console.log(cards[0].toString());

function drawCard({cards}){
    return cards.pop();
}

const myCard = drawCard(deck);

const [firstCard] = cards;

console.log(firstCard.toString());

const[, secondCard,,fourthCard] = cards;
console.log(`Second Card: ${secondCard.toString()}`)
console.log(`Second Card: ${fourthCard.toString()}`)

const conastaDeck = [...cards, ...cards,{suit: 'Joker',rank: ''}];

/*****************************************************
 * Functions, function expressions, and arrow syntax
 *****************************************************
 */
 function Card(suit, rank){
    this.suit = suit;
    this.rank = rank;
}


Card.prototype.toString = function(){
    return `${this.rank} of ${this.suit}`;
}
Card.prototype.brand = 'Standard Hoyle';
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

