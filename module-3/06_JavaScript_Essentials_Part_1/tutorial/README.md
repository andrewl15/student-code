# JavaScript Essentials (Part 1) tutorial

In this tutorial, you'll write and run some JavaScript code using Node.js.

To start, open this `tutorial` folder in Visual Studio Code and open the `tutorial.js` file. The file is empty now, but you'll add code as you read through this tutorial.

## Step One: Print messages to the console

The `console.log()` method prints the value passed to it. It displays in the terminal when you run the file.

Start with printing some messages to the console:

```js
console.log('Hello!');
console.log('I am learning JavaScript today.');
```

Save the file by going to the **File** menu and selecting **Save**, or press **Ctrl+S**.

Now go to the Terminal pane in Visual Studio Code. If it's not already open, go to the **Terminal** menu and select **New Terminal**.

In the terminal, type `node tutorial.js` and press **Enter**. The messages you passed to `console.log()` print on the command line.

> NOTE: Be sure to save the file before running the `node` command.

## Step Two: Create variables and print messages

Next, you'll declare some variables and print messages using those variables.

There are two variable types in JavaScript:

- Variables declared with `let` allow you to change the variable's value later.
- Variables declared with `const` have fixed values, meaning they require a value when declared, and you can't change that value later.

It's a best practice in JavaScript to always declare variables with `const` unless you know you need to change or assign its value later.

Start by declaring two `const` variables to store your name and a cookie count of `12`:

```js
const myName = 'Alex Brownell';
const cookieCount = 12;
```

Next, declare another `const` for a message that you'll display with your name and cookie count:

```js
const message = `My name is ${myName} and I have ${cookieCount} cookies.`;
console.log(message);
```

Notice the use of backticks (\`) instead of single quotes like you used for `myName`. This is a **template literal** that allows you to add variables or expressions into a string. Template literals are preferred over string concatenation as they're easier to read and less prone to errors.

Save and run the file again with `node tutorial.js`. It now prints the new message you just added.

Suppose someone ate a cookie. Update the `cookieCount` and print a new message:

```js
cookieCount = cookieCount - 1;
console.log(`Someone ate one, so now there are ${cookieCount} cookies.`);
```

Save and run the file again with `node tutorial.js`. It didn't print the new message, and an error occurred.

Look at the error message that printed:

```plaintext
C:\Users\Student\Code\JavaScript_Essentials_Part_1\tutorial\tutorial.js:13
cookieCount = cookieCount - 1;
            ^

TypeError: Assignment to constant variable.
```

The first line shows the file and line number where the error occurred. The second line indicates precisely where the error occurred. Then, it shows the error message, saying you can't assign a new value to a `const` after it's declared.

Go back to where you declared `cookieCount` and change `const` to `let`:

```js
let cookieCount = 12; // change const to let
```

Now save and run the file again. No error occurs, and it prints the new message with the updated count.

## Step Three: Create and call a function

Functions are a core feature of nearly every programming language. They allow you to reuse code and move complex logic out of the way to make your code easier to read and understand.

Suppose you want to get the first word of a string, meaning the characters from the start of a string to the first space. That function might look like:

```js
function getFirstWordOfString(text) {
  const spaceIndex = text.indexOf(' ');
  return text.substring(0, spaceIndex);
}
```

The method `getFirstWordOfString()` takes a parameter named `text`, finds the first instance of a space, and returns the substring from the start of the string to the index of the space. Note that the character at that index isn't included in the substring.

Add that function to `tutorial.js` and use it to get your first name:

```js
const myFirstName = getFirstWordOfString('myName');
console.log(`My first name is "${myFirstName}"`);
```

Similar to the code in the previous step, the string is a template literal to place a variable in a string. A variable isn't required; you can use any valid expression that returns a string. For example, you can just call the `getFirstWordOfString()` method without the intermediate variable:

```js
console.log(`The first word of message is "${getFirstWordOfString(message)}"`);
```

When you run this code, it prints your first name and the first word of `message`:

```plaintext
My first name is "Alex"
The first word of message is "My"
```

## Step Four: Use `if-else` statements for conditional logic

Another core feature of JavaScript is `if-else` statements. You use them to perform actions only if a given condition is `true` and optionally do something else when the condition is `false`.

Imagine you're writing an application that gives the weather and offers recommendations based on the expected conditions. For example, it might suggest bringing a coat when it's chilly.

Start by declaring a `forecast` using an object literal. This simulates the data you might get from a weather forecast:

```js
let forecast = {
  highTemperatureF: 55,
  precipitationExpected: false
};
```

Recall that you use dot notation to access the object properties, like `forecast.highTemperatureF` or `forecast.precipitationExpected`.

If the expected high temperature is under 65 degrees, you recommend bringing a coat:

```js
console.log(`Expected high temperature: ${forecast.highTemperatureF}`);
if (forecast.highTemperatureF < 65) {
  console.log("Don't forget to bring a coat today.");
}
```

> NOTE: Notice the use of double quotes instead of single quotes. That's because the apostrophe in the word "Don't" would get interpreted as a single quote, ending the string early. To use single quotes, you'd need to place an _escape character_ before the apostrophe to tell JavaScript to treat it as an ordinary character. In JavaScript, the escape character is a backslash: `console.log('Don\'t forget to bring a coat today.');`

The `else` part is what occurs if a given condition isn't true:

```js
if (forecast.precipitationExpected) {
  console.log('The chance of precipitation is high.');
} else {
  console.log('The chance of precipitation is low.');
}
```

When you run this code, it generates the output:

```plaintext
Expected high temperature: 55
Don't forget to bring a coat today.
The chance of precipitation is low.
```

## Step Five: Work with arrays in loops

Arrays are another core feature of any programming language. They enable you to store multiple related values in a single variable.

Suppose you have the expected high temperatures for the next five days. An array would be a good way to store that information:

```js
const forecastTemperatures = [72, 78, 58, 79, 74];
```

To list all the expected temperatures, you can use a `for...of` loop to display them:

```js
for (const temperature of forecastTemperatures) {
  console.log(temperature);
}
```

When you run that code, it prints each element in the array:

```plaintext
72
78
58
79
74
```

In the preceding example you could've used a standard `for` loop with an increment counter. However, if you're working with an array or other collection of data, it's often preferable to use a `for...of` loop unless there's a reason to use that increment counter. The following example uses a `for` loop to get the highest temperature and how many days from today it occurs:

```js
let highestTemperatureValue = forecastTemperatures[0];
let highestTemperatureIndex = 0;
for (let i = 1; i < forecastTemperatures.length; i++) {
  if (forecastTemperatures[i] > highestTemperatureValue) {
    highestTemperatureValue = forecastTemperatures[i];
    highestTemperatureIndex = i;
  }
}
```

The variables `highestTemperatureValue` and `highestTemperatureIndex` track the highest temperature value and the index for that value. The loop starts at `1` because you can set the initial value of those variables to element index `0`. On each loop, it checks if the forecast temperature is higher than the stored value, and if it's higher it stores that value and index.

After the loop, you can print the highest temperature and in how many days away:

```js
console.log(`The highest temperature is ${highestTemperatureValue}.`);

if (highestTemperatureIndex === 0) {
  console.log('It is tomorrow.');
} else {
  console.log(`It is ${highestTemperatureIndex + 1} days away.`); // day + 1 because the array is zero-based
}
```

Running this code prints this to the console:

```plaintext
The highest temperature is 79.
It is 4 days away.
```

## Step Six: Modify data in an array

In the preceding example, the future high temperature data is only valid for the current day. When it's tomorrow, the first element represents the _current_ day, and you no longer have five days of future temperatures.

To handle that, you can remove the first element from the array and add a new one to the end.

The `shift()` method removes the first element from an array and returns that value:

```js
const todayTemperature = forecastTemperatures.shift();
// 72 is removed and array now contains [78, 58, 79, 74]
console.log(`Today's high temperature is ${todayTemperature}.`);
```

It's not necessary to store the return value if you don't have a need for it, but this example prints it as today's temperature.

To add a new value to the end of an array, you use the `push()` method on the array passing in the new value:

```js
const newTemperature = 81;
forecastTemperatures.push(newTemperature);
```

To see the new values, you can loop through the array again and see the updated values:

```js
for (const temperature of forecastTemperatures) {
  console.log(temperature);
}
```

The code from this step produces:

```plaintext
Today's high temperature is 72.
78
58
79
74
81
```

## Next steps

* In the reading, you saw an example of `Intl.NumberFormat()` to format currency. That method is capable of formatting many different numerical values and measurements, including temperatures. Use this example to print the formatted Fahrenheit temperatures from this tutorial:
  * `Intl.NumberFormat('en-US', {style: 'unit', unit: 'fahrenheit'}).format(value)`
* Write a method that returns the high temperature for a given day passed in as a parameter. (You may need to pass in `forecastTemperatures` as a parameter too).
  * Remember that arrays are zero-based and the `forecastTemperatures` array contains future temperature, not the current day's.
  * What happens if you pass in an invalid value like an array index that doesn't exist or a value that's not numeric? Can you think of a way to check for valid values?

### Conditional operator

There's some duplicated code in Step Five. In the `if-else` block, the `console.log(` and the `It is` occurs in both the `if` and `else` block.

You could replace the `if-else` with a single line using the conditional operator `?`, also called the _ternary_ operator because it has three parts. An expression with a conditional operator takes three operands separated by a `?` and `:`. The expression has the form:

```
condition ? code for true : code for false
```

The following code uses nested template literals with the conditional operator. The condition is `highestTemperatureIndex === 0`. If it's true, it adds `tomorrow` to the string, but if it's false, it uses another template literal to print the number of days away:

```js
console.log(
  `It is ${
    highestTemperatureIndex === 0 ? 'tomorrow'
      : `${highestTemperatureIndex + 1} days away`
  }.`
);
```

You can replace the `if-else` block with this expression and it has the same effect. Try changing the numbers in the array, specifically making the first element the highest, to see the different output to the console.
