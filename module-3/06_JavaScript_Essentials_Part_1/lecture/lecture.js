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


// Use const for values that should not change
// Use uppercase for constants


// Variables are not typed, they can hold any value


// Variables that don't have a value are undefined


// null also represents an empty value, but is explicitly assigned


//Objects literals are simple key-value pairs.

// show object properties

// Use dot notation to access properties

// Using const with an object only prevents reassignment



/* *****************
 *   Type coercion
 * *****************
 */

// JavaScript will use type coercion when the type isn't what is expected
// With + if both values are numbers, it does arithmetic addition


// If one of the values is a string, it does string concatenation


// This can also happen with comparison operators
// == is loose equality, and can be true even if the types are different

// === is strict equality, and will only be true if the types are the same


// Type coercion can lead to some interesting behaviors in JavaScript


// It's best to avoid implicit type coercion and convert types explicitly.
// This is more clear and can help prevent bugs.
let temp = '62';
let tempAsNumber = Number.parseInt(temp);
console.log(`${temp} adada`);
// Converting strings to numbers
let errorNumber = 'This is not a number';

// If string can't be converted, it will return NaN



/* ********************
 *   String methods
 * ********************
 */
// JavaScript convention prefers single quotes for strings
const message = 'Welcome to JavaScript';
console.log(message.includes('Java'));
console.log(message.includes('java'));


// Check if a string contains another string?
console.log(message.indexOf('to'));
console.log(message.indexOf('a'));
console.log(message.lastIndexOf('a'));

// Locate position of a string within another string
const newString = message.replace('JavaScript', 'TypeScript');
console.log(`the original ${message} the replaced ${newString}`);

// Replace part of a string
console.log(message.toLowerCase());
console.log(message.toUpperCase());

// Extract part of a string
// The ending index is not included


// Convert to all uppercase and all lowercase



/* ********************
 *   Arrays and Loops
 * ********************
 */

// Create an array.
let foods = ['pizza', 'burgers', 'bacon','toast'];
console.log(foods.length);

// Array size is dynamic


// Add an element to the end of the array using push
foods.push('coffee');
console.table(foods);

// for loop to loop through an array, i is a counter for the index
for(let i = 0; i < foods.length; i++){
  console.log(foods[i]);
}

// for...of loop to loop through the array, no access to index
for(let item of foods){
  console.log(item);
}

/* 
 * Some other array methods that modify the array instance are:
 *    pop(), unshift(), shift(), splice(), and reverse()
 */
console.log(foods.pop());

/* 
 * Some array methods that don't modify the array instance are:
 *    includes(), indexOf(), lastIndexOf(), slice(), and join()
 */


/* ********************
  *   Functions
  * ********************
  */

/**
 * Functions declarations start with the word function.
 * They don't have a return type and the naming convention is camel-case.
 */

// Declare a `helloWorld` function with no parameters


// Call the function


// Declare a `sayHello` function that accepts a name parameter
function sayHello(name){
  console.log('Hello' + name);
}

// Call the function passing a name
sayHello('Hello');

// Call the function without passing a name
sayHello();

// Update the function to use a default value for the name parameter
function sayHello(name = 'Somebody'){
  console.log('Hello' + name);
}

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
function add(num1, num2, num3 = 0){
  console.log(num1 + num2+num3);
}

// Try to call the `add` function with 2 arguments
add(8,2,2);
add(2,4,6,8,10);

// Use "rest parameters" to pass an arbitrary number of arguments
function rest(...numbers){
  let sum = 0;
  for(let number of numbers){
    sum += number;
  }
  console.log(sum);
}

rest(1,2,3,4,5,6,7,8,9,10);
