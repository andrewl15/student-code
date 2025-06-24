// Step One: Print messages to the console
console.log('Hello!');
console.log('I am learning JavaScript today.');


// Step Two: Create variables and print messages
const myName = 'Alex Brownell';
let cookieCount = 12; // const cookieCount = 12;

const message = `My name is ${myName} and I have ${cookieCount} cookies.`;
console.log(message);

cookieCount = cookieCount - 1;
console.log(`Someone ate one, so now there are ${cookieCount} cookies.`);


// Step Three: Create and call a function
function getFirstWordOfString(text) {
  const spaceIndex = text.indexOf(' ');
  return text.substring(0, spaceIndex);
}

const myFirstName = getFirstWordOfString(myName);
console.log(`My first name is "${myFirstName}"`);
console.log(`The first word of message is "${getFirstWordOfString(message)}"`);


// Step Four: Use if-else statements for conditional logic
let forecast = {
  highTemperatureF: 55,
  precipitationExpected: false
};

console.log(`Expected high temperature: ${forecast.highTemperatureF}`);
if (forecast.highTemperatureF < 65) {
  console.log("Don't forget to bring a coat today.");
  // console.log('Don\'t forget to bring a coat today.');
}

if (forecast.precipitationExpected) {
  console.log('The chance of precipitation is high.');
} else {
  console.log('The chance of precipitation is low.');
}


// Step Five: Work with arrays in loops and array functions
const forecastTemperatures = [72, 78, 58, 79, 74];
for (const temperature of forecastTemperatures) {
  console.log(temperature);
}

let highestTemperatureValue = forecastTemperatures[0];
let highestTemperatureIndex = 0;
for (let i = 1; i < forecastTemperatures.length; i++) {
  if (forecastTemperatures[i] > highestTemperatureValue) {
    highestTemperatureValue = forecastTemperatures[i];
    highestTemperatureIndex = i;
  }
}

console.log(`The highest temperature is ${highestTemperatureValue}.`);

if (highestTemperatureIndex === 0) {
  console.log('It is tomorrow.');
} else {
  console.log(`It is ${highestTemperatureIndex + 1} days away.`); // day + 1 because the array is zero-based
}
// One line with ternary operator
//console.log(`It is ${highestTemperatureIndex === 0 ? 'tomorrow' : `${highestTemperatureIndex + 1} days away`}.`);


// Step Six: Modify data in an array
const todayTemperature = forecastTemperatures.shift();
console.log(`Today's high temperature is ${todayTemperature}.`);
const newTemperature = 81;
forecastTemperatures.push(newTemperature);
for (const temperature of forecastTemperatures) {
  console.log(temperature);
}
