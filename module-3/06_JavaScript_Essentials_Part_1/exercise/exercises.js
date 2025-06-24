/* Exercise 0 - sumNumbers
    Given two number values, return their sum.

    Sample inputs and outputs:
      sumNumbers(1, 2) → 3
      sumNumbers(3, -2) → 1
      sumNumbers(1.5, 4.5) → 6
*/
function sumNumbers(x, y) {
  return x + y;
}

/* Exercise 1 - sumAny
    Given two values, return their sum. Attempt to convert non-numeric values to numbers, returning NaN
    if the conversion fails. If any argument is missing, use a default value of 0.

    Sample inputs and outputs:
      sumAny(4, '-2') → 1
      sumAny(1, 2.5) → 3.5
      sumAny() → 0
      sumAny(1 + true) → NaN
*/
function sumAny(x = 0, y = 0) {
  const num1 = Number(x);
  const num2 = Number(y);
  if(Number.isNaN(num1) || Number.isNaN(num2) || y === true){
    return NaN;
  }
  return num1 + num2;
}

/* Exercise 2 - createPet
    Given a type, name, age, and description, return a *pet* object with the following properties:
      - type - the type of the pet in lowercase
      - name - the name of the pet
      - age - the age of the pet
      - isSenior - true if the pet is 10 years or older
      - description - the description of the pet
    Ensure that you remove any leading or trailing whitespace from string properties.

    Sample:
      createPet('Cat', 'Whiskers', 10, 'A fluffy cat.') →
          {
            type: 'cat',
            name: 'Whiskers',
            age: 10,
            isSenior: true,
            description: 'A fluffy cat.'
          }
*/
function createPet(type, name, age, description) {
  const pet = {
    type: type.toLowerCase().trim(),
    name: name.trim(),
    age: age,
    isSenior: age >= 10 ? true : false,
    description: description.trim()
  }
  return pet;
}

/* Exercise 3 - isTeen
    Given 3 int values, return true if the number is in the range 13 to 19 inclusive.

    Sample inputs and outputs:
      isTeen(10) → false
      isTeen(13) → true
      isTeen(15) → true
*/
function isTeen(number) {
  let age = Number(number);
  return age >= 13 && age <= 19? true : false;
}

/* Exercise 4 - formatAddress
    Given an object with the following properties:
      {
        streetNumber: number,
        streetName: string,
        streetType: string,
        city: string,
        state: string,
        zip: string
      }

    Return a formatted string with the following format:
      streetNumber streetName streetType
      city, state zip

    Sample inputs and outputs:
      formatAddress({ streetNumber: 526, streetName: 'Market', streetType: 'St', city: 'Philadelphia', state: 'PA', zip: '19106' }) →
        '526 Market St\nPhiladelphia, PA 19106'
*/
function formatAddress(address) {
  return(
  `${address.streetNumber} ${address.streetName} ${address.streetType}\n${address.city}, ${address.state} ${address.zip}`)
}

/* Exercise 5 - average
    Given an array of numbers, return the average of all the values, as a string rounded to 2 decimals.
    To calculate the average, add all the numbers together and divide by the number of values.
    If the array is empty, return zero.

    Sample inputs and outputs:
      average([10, 15, 20]) → '15.00'
      average([6, 17, 45, 24.5]) → '23.13'
      average([]) → '0.00'
*/
function average(numberArray) {
  if(numberArray.length === 0){
    return '0.00';
  }
  let sum = 0.00;
  for(const number of numberArray){
    sum += number;
  }
  return(sum / numberArray.length).toFixed(2);
}

/* Exercise 6 - getItemNumber
    Determine the item number from the product code.
    The item number is determined based on the following rules:
      - If the product code begins with a '0', it's always the last 8 characters of the product code.
      - If the product code contains 'XID' (but doesn't start with '0'), it begins after 'XID' and
        continues until there is a '-' or to the end of the product code.
      - Otherwise the item number is the first 10 characters of the product code.

    Sample inputs and outputs:
      getItemNumber('0-324-ZID12345678') → '12345678'
      getItemNumber('0A12012345678') → '12345678'
      getItemNumber('XID012345-001') → '012345'
      getItemNumber('XID01234') → '01234'
      getItemNumber('1234567890A213B-020') → '1234567890'
      getItemNumber('1234567890') → '1234567890'
*/
function getItemNumber(productCode) {
  if(productCode[0] === '0'){
    return productCode.slice(productCode.length-8);
  }else if(productCode.includes('XID')){
    if(!productCode.includes('-')){
      return productCode.substring(3,productCode.length);
    }else{
      return productCode.substring(3,productCode.indexOf('-'));

    }
  }else{
    return productCode.substring(0,10);
  }
}

/* Exercise 7 - getItemStyle
    Determine the item style code from the product code.
    The style code is not always present, but when it is, it is either 3 or 4 digits following
    a '-' at the end of the product code.

    Sample inputs and outputs:
      getItemStyle('XID012345-001') → '001'
      getItemStyle('1234567890-A213B-0202') → '0202'
      getItemStyle('0-324-ZID12345678') → ''
      getItemStyle('1234567890-A213B') → ''
      getItemStyle('1234567890-A2') → ''
*/
function getItemStyle(productCode) {
  const lastDashIndex = productCode.lastIndexOf('-');
  const lastPart = productCode.substring(lastDashIndex + 1);

  if (productCode.length - lastDashIndex === 4 || productCode.length - lastDashIndex === 5){
    return lastPart;
  }
  return '';
}

/* Exercise 8 - extractOdds
    Write a function that takes array of numbers, removes the odd numbers from that array,
    and returns a new array consisting of only the odd numbers removed.

    Notes:
      - When removing values from an array, use a while loop to better control the iteration.
      - Take notice in the sample output that the original array is modified because arrays are reference types.

    Sample inputs and outputs:
      let array1 = [1, 2, 3, 4, 5];
      extractOdds(array1); → [1, 3, 5]
      console.log(array1); // [2, 4]
*/
function extractOdds(values) {
  let i = 0;
  let odds = [];
  while(i < values.length){
    if(values[i] % 2 !== 0){
      odds.push(values[i]);
      values.splice(i, 1);
    }else{
      i++;
    }
  }
  return odds;
}


/* Exercise 9 - matchingStrand
    Given a DNA strand, return the complementary strand. A and T are complements of each other, as are C and G.
    Notes:
     - The input strand contain lowercase and uppercase letters, but the output must always be in uppercase.
     - Do not modify the input string.
     - If any other character is found, return null.

    Sample inputs and outputs:
      matchingStrand('') → ''
      matchingStrand('Aa') → 'TT'
      matchingStrand('ATCG') → 'TAGC'
      matchingStrand('gcgtaat') → 'CGCATTA'
      matchingStrand('zattag') → null
*/
function matchingStrand(dnaStrand = '') {
  let output = '';
  for(const char of dnaStrand){
    if(char.toLowerCase() === 'a'){
      output += 'T';
    }else if(char.toLowerCase() === 't'){
      output += 'A';
    }else if(char.toLowerCase() === 'c'){
      output += 'G';
    }else if(char.toLowerCase() === 'g'){
      output += 'C';
    }else{
      return null;
    }
  }
  return output;
}

/* Exercise 10 - catsAndDogs
    Write a function that takes an array of Pet objects which have a 'type' property indicating the animal type.
    Count the number of cats, dogs, and other animals in the array, then returns a new object containing the counts like follows:
      {
        catCount: the number of cats in the array,
        dogCount: the number of dogs in the array,
        otherCount: the number of other animals in the array
      }

    Notes:
      - If there is no type property, count the animal as 'other'.
      - The object must always contain all three properties, with the count set to zero if there are no animals of that type.
      - If the array is empty or nothing is passed in, return an object with 0 for all the counts.

    Sample inputs and outputs:
      catsAndDogs([{type: 'cat'}, {type: 'dog'}, {type: 'cat'}]) → {
        catCount: 2,
        dogCount: 1,
        otherCount: 0
      }
      catsAndDogs([{type: 'bird'}, {type: 'cat'}]) → {
        catCount: 1,
        dogCount: 0,
        otherCount: 1
      }
*/
function catsAndDogs(petArray = []) {
  const petCount = {
    catCount: 0,
    dogCount: 0,
    otherCount: 0
  }
  if(petArray.length == 0){
    return petCount;
  }
  for(const pet of petArray){
    if(pet.type === 'cat'){
      petCount.catCount += 1;
    }else if(pet.type === 'dog'){
      petCount.dogCount += 1;
    }else {
      petCount.otherCount += 1;
    }
  }
  return petCount;
  
}

/* Exercise 11 - sumAll
    Write a function called sumAll that takes an unknown number of parameters, adds them all together, and returns the sum.
    Notes:
     - Attempt to convert any strings to numbers. Any non-numeric values of any type should be ignored.
     - If no parameters are passed, return 0.

    Sample inputs and outputs:
      sumAll(1, 2, 3) → 6
      sumAll(1, -2, 3, 6) → 8
      sumAll(1, '-3') → -2
      sumAll(true, null, 'a') → 0
      sumAll() → 0
*/
function sumAll(...numbers) {
  let sum = 0;
  for (const num of numbers) {
    if (typeof num !== "boolean" && !isNaN(Number(num))) {
      sum += Number(num);
    }
  }

  return sum;

}

/* Exercise 12 - sumDigits
    Write a function called sumDigits that takes a whole number, and returns the sum of all the digits.
    Notes:
      - If the number is negative, the resulting sum should also be negative.
      - If no parameter is passed, return 0.

    Sample inputs and outputs:
      sumDigits(111) → 3
      sumDigits(-111) → -3
      sumDigits() → 0
*/
function sumDigits(number = 0) {
  let sum = 0;
  const stringNumber = String(number);
  for(let num of stringNumber){
    if(num !== '-'){
      sum += Number(num);
    }
  }
  return stringNumber[0] === '-' ? sum * -1 : sum;
}

// do not modify the following line
export { sumNumbers, sumAny, createPet, isTeen, formatAddress, average, getItemNumber, getItemStyle, extractOdds, matchingStrand, catsAndDogs, sumAll, sumDigits };
