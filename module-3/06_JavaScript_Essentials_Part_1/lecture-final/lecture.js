/* ***************************
 *  Variables and data types
 * ***************************
 */

// Use let for values that will change
// Use camelCase for variable names
let number = 42;
let otherNumber = 8;
console.log(number);
console.log(otherNumber);
let firstName = "Henry";
console.log(firstName);
let lastName = 'Edwards';
console.log(lastName);
let possession = "Edward's";
console.log(possession);
// Use const for values that should not change
// Use uppercase for constants
const PI = 3.1415;
const DAYS_IN_A_WEEK = 7;
console.log(PI);
// DAYS_IN_A_WEEK = 8;
console.log('There are ' + DAYS_IN_A_WEEK + ' days in a week');

// Variables are not typed, they can hold any value
let value = 10;
console.log('The type is ' + (typeof value));
console.log('The value is ' + value);
value = 'Ten';
console.log('The value is now ' + value);
console.log('The type is ' + (typeof value));
value = true;
console.log('This value has changed again ' + value);
// string interpolation
console.log(`The value of the variable is ${value} and the type is ${typeof value}`);

// Variables that don't have a value are undefined
let noValue;
console.log(noValue);

// null also represents an empty value, but is explicitly assigned
noValue = null;
console.log(`This is now ${noValue} it's the type ${typeof noValue}`);

//Objects literals are simple key-value pairs.
const person = {
  firstName: "Henry",
  lastName: "Edwards",
  age: 42,
  pets: ["Max","Ceasar","Eddie","Tasha"]
};
console.log(person.firstName + " " + person.lastName);
// show object properties
console.log(person);
console.table(person);
// Use dot notation to access properties
console.log(`${person.firstName} is ${person.age} years old`);
// Using const with an object only prevents reassignment
person.firstName = "Bill";
person.lastName = "Reeves";
person.age = 52;
console.log(`${person.firstName} ${person.lastName} is ${person.age} years old.`);
// person = {
//   firstName: 'Mimi',
//   lastName: 'Malone',
//   age: 22
// };
// const greeting = "Hello World!";
// greeting = 'Hello Mars!';

const ages = [24,42,52,106];
console.table(ages);
ages[2] = 8;
console.table(ages);
/* *****************
 *   Type coercion
 * *****************
 */

// JavaScript will use type coercion when the type isn't what is expected
// With + if both values are numbers, it does arithmetic addition
let x = 10;
let y = 5;
console.log(x + y);
y = '5';
// If one of the values is a string, it does string concatenation
y = '5';
console.log(x + y);

let first = 2;
let second = 2;
console.log(first + second);
first = '2';
second = '2';
console.log(first + second);
first = 2;
second = 2;
console.log(first + second - first);
first = '2';
second = '2'
console.log(first + second - first);
// This can also happen with comparison operators
// == is loose equality, and can be true even if the types are different
second = 2;
console.log(`${typeof first} ${typeof second}`);
console.log(first == second);
// === is strict equality, and will only be true if the types are the same
console.log(`Strict equality ${first === second}`);

// Type coercion can lead to some interesting behaviors in JavaScript
let votingAge = -102;
if(votingAge){
  console.log('Yeah, you can vote!');
} else {
  console.log('Sorry, you need to grow up.')
}

// It's best to avoid implicit type coercion and convert types explicitly.
// This is more clear and can help prevent bugs.
let temperature = '62';
let temperatureAsANumber = Number.parseInt(temperature);
console.log(`${temperature} is a ${typeof temperature} and ${temperatureAsANumber} is a ${typeof temperatureAsANumber}`);

// Converting strings to numbers


// If string can't be converted, it will return NaN
let errorNumber = 'This is not a number';
let errorConvert = Number.parseFloat(errorNumber);
console.log(errorConvert);
console.log(errorNumber * temperatureAsANumber);
console.log(`errorConvert is ${errorConvert} it is of type ${typeof errorConvert}`);

/* ********************
 *   String methods
 * ********************
 */
// JavaScript convention prefers single quotes for strings
const message = 'Welcome to JavaScript';

// Check if a string contains another string?
console.log(message.includes('Java'));
console.log(message.includes('java'));
// Locate position of a string within another string
console.log(message.indexOf('to'));
console.log(message.indexOf('a'));
console.log(message.lastIndexOf('a'));
// Replace part of a string
const newString = message.replace('JavaScript','TypeScript');
console.log(`The original '${message}' the replaced '${newString}'`);

// Extract part of a string
// The ending index is not included
console.log(message.substring(12,14));
console.log(message.substring(12));
// Convert to all uppercase and all lowercase
console.log(message.toUpperCase());
console.log(message.toLowerCase());
console.log(message);

/* ********************
 *   Arrays and Loops
 * ********************
 */

// Create an array.
let foods = ['pizza','burgers','bacon','toast'];

// Array size is dynamic
console.log(foods.length);

// Add an element to the end of the array using push
foods.push('coffee');
console.table(foods);

// for loop to loop through an array, i is a counter for the index
for(let i = 0; i < foods.length; i++){
  console.log('food at index ' + i + ' is ' + foods[i]);
}

// for...of loop to loop through the array, no access to index
for(let item of foods){
  console.log(item);
}

/* 
 * Some other array methods that modify the array instance are:
 *    pop(), unshift(), shift(), splice(), and reverse()
 */
// pop removes and returns the last item
console.log(foods.pop());
console.table(foods);

// unshift adds to the front
foods.unshift('Ramen');
console.table(foods);
console.log(foods.shift());
console.table(foods);

foods.splice(1,0,'noodles','soup');
console.table(foods);
/* 
 * Some array methods that don't modify the array instance are:
 *    includes(), indexOf(), lastIndexOf(), slice(), and join()
 */
console.log(foods.includes('burgers'));
foods.push('burgers');
console.log(foods.indexOf('burgers'));
console.table(foods);
console.log(foods.lastIndexOf('burgers'));

console.log(foods.slice(2,4));
console.table(foods);
console.log(foods.join(','));


/* ********************
  *   Functions
  * ********************
  */

/**
 * Functions declarations start with the word function.
 * They don't have a return type and the naming convention is camel-case.
 */

// Declare a `helloWorld` function with no parameters
function helloWorld(){
  console.log('Hello World!');
}

// Call the function
helloWorld();

// Declare a `sayHello` function that accepts a name parameter
function sayHello(name){
  console.log('Hello ' + name);
}

// Call the function passing a name
sayHello('Henry');

// Call the function without passing a name
sayHello();

// Update the function to use a default value for the name parameter
function sayHello(name = 'Somebody'){
  console.log('Hello ' + name);
}

sayHello();

/* Note: Function Overloading is not available in JavaScript. 
 * If you declare multiple functions with the same name, the earlier ones are overridden 
 * and the most recent one will be used.
*/

// Declare an `add` function to add two numbers
function add(num1, num2){
  console.log(num1 + num2);
}
add(2,5);
// Declare an `add` function to add three numbers
function add(num1, num2, num3 = 0 ){
  console.log(num1 + num2 + num3);
}
add(2,5,10);
add(2,4,6,8,10);

// Try to call the `add` function with 2 arguments


// Use "rest parameters" to pass an arbitrary number of arguments
function addAll(...numbers){
  let sum = 0;
  for(let i = 0; i < numbers.length; i++){
    sum += numbers[i];
  }
  console.log(sum);
}
addAll(1,2,3,4);
addAll(5,6,7,8,9,10,11,12,13,14);
console.log(`Goodbye people!`);
