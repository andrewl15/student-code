# JavaScript Essentials Part 1 exercises

For this exercise, you must write JavaScript functions matching the descriptions found in the comments in the `exercises.js` file. The comments for the problem describe the required function behavior. Each problem description includes a few sample inputs and outputs to illustrate the expected behavior. Begin by ensuring you understand how the provided sample inputs generate the sample output, then write the function.

## Example

As an example, the first problem is already complete:

```javascript
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
```

## Running the exercise tests

**Before** you run the tests for the first time, you must install the Node packages required by the tests. You do this by running `npm install` on the command-line from the `exercise` directory (the same location where the `package.json` file is). You only need to do this once.

To run the tests, you run the command `npm run test` from the same location. After completing each function, you can run the unit tests to verify the function is correct.

If you want to test and debug your code without running the tests, you can run the exercise file by running `npm run start` or `node exercises.js`. Remember, you must add calls to the functions:

```js
console.log(sumNumbers(1, 2));
console.log(sumNumbers(3, -2));
```
