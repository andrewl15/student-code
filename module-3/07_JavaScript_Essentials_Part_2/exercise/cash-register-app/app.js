import { CashRegister } from './cashRegister.js';
import { getProducts } from './product.js';
import { PAYMENT_TYPES } from './paymentType.js';
import { formatCurrency } from './util.js';
import { getSalesTotal, getSalesTotalByPaymentType } from './sale.js';
import readlineSync from 'readline-sync';

const MAIN_MENU_OPTIONS = [ 'List Products', 'Add Product to Order', 'Remove Product from Order', 'Review Order', 'Ring Up Sale', 'Sales Report' ];
const LINE_SEPARATOR = '---------';
const cashRegister = new CashRegister();

let optionSelected;

do {
  optionSelected = promptForMenuOption();
  let optionText = MAIN_MENU_OPTIONS[optionSelected - 1];

  switch (optionText) {
    case 'List Products':
      console.log('Product listing');
      console.log(LINE_SEPARATOR);
      getProducts().forEach((product) => {
        console.log(product.toString());
      });
      break;
    case 'Add Product to Order':
      const productId = readlineSync.questionInt('Enter the product ID: ');
      const quantity = readlineSync.questionInt('Enter the quantity: ');
      if (cashRegister.addProductToOrder(productId, quantity)) {
        console.log('Product added to order.');
      } else {
        console.log('Insufficient inventory or product not found.');
      }
      break;
    case 'Remove Product from Order':
      const productIdToRemove = readlineSync.questionInt('Enter the product ID to remove: ');
      if (cashRegister.removeProductFromOrder(productIdToRemove)) {
        console.log('Product removed from order.');
      } else {
        console.log('Product not found in the order.');
      }
      break;
    case 'Review Order':
      console.log(LINE_SEPARATOR);
      const orderSummaryReview = cashRegister.getOrderSummary();
      if (orderSummaryReview.products && orderSummaryReview.products.length > 0) {
        orderSummaryReview.products.forEach((product) => {
          console.log(`Name: ${product.name}\n  Price: ${formatCurrency(product.price)}\n  Quantity: ${product.quantity}`);
        });
        console.log(LINE_SEPARATOR);
      } else {
        console.log('There are no products in the current order.');
      }
      if (orderSummaryReview.total) {
        console.log(`Your order total is ${formatCurrency(orderSummaryReview.total)}`);
      }
      break;
    case 'Ring Up Sale':
      const orderSummaryRingUp = cashRegister.getOrderSummary();
      const productsInOrder = orderSummaryRingUp.products;
      if (productsInOrder && productsInOrder.length === 0) {
        console.log('Your order is empty. Please add products to the order before ringing up a sale.');
        break;
      }
      const paymentType = promptForPaymentType();
      cashRegister.ringUpSale(paymentType);
      const shouldPrintReceipt = readlineSync.keyInYNStrict('Print receipt?');
      if (shouldPrintReceipt) {
        printReceipt(orderSummaryRingUp);
      }
      break;
    case 'Sales Report':
      console.log('Sales Report');
      console.log(LINE_SEPARATOR);
      console.log(`Total Sales: ${formatCurrency(getSalesTotal())}`);
      Object.values(PAYMENT_TYPES).forEach((paymentType) => {
        console.log(`${paymentType}: ${formatCurrency(getSalesTotalByPaymentType(paymentType))}`);
      });
      break;
  }

  if (optionSelected !== 0) { // if not exiting, prompt enter to continue
    promptEnterToContinue();
  }

} while (optionSelected !== 0) // 'Exit' = 0

function promptForMenuOption() {
  console.clear();
  console.log('Main Menu');
  console.log(LINE_SEPARATOR);

  MAIN_MENU_OPTIONS.forEach((option, index) => {
    console.log(`[${index + 1}] ${option}`);
  });
  console.log('[0] Exit');
  let optionSelected = readlineSync.question('Select an option: ');
  while (!isValidOption(optionSelected, 0, MAIN_MENU_OPTIONS.length)) {
    console.log('Invalid option. Please try again.');
    optionSelected = readlineSync.question('Select an option: ');
  }
  return Number.parseInt(optionSelected);
}

function promptForPaymentType() {
  console.clear();
  console.log('Payment Types');
  console.log(LINE_SEPARATOR);

  Object.values(PAYMENT_TYPES).forEach((paymentType, index) => {
    console.log(`[${index + 1}] ${paymentType}`);
  });
  let paymentTypeSelected = readlineSync.question('Select a payment type: ');
  while (!isValidOption(paymentTypeSelected, 1, Object.values(PAYMENT_TYPES).length)) {
    console.log('Invalid payment type. Please try again.');
    paymentTypeSelected = readlineSync.question('Select a payment type: ');
  }
  return Object.values(PAYMENT_TYPES)[Number.parseInt(paymentTypeSelected) - 1];
}

function promptEnterToContinue() {
  console.log(LINE_SEPARATOR);
  readlineSync.question('Press Enter key to continue', {hideEchoBack: true, mask: ''});
  console.clear();
}

/**
 * Validates if the option is a number and within the allowed range.
 * @param {number} option the option selected by the user
 * @param {number} minAllowed the minimum allowed value inclusively
 * @param {number} maxAllowed the maximum allowed value inclusively
 * @returns
 */
function isValidOption(option, minAllowed, maxAllowed) {
  return !(Number.isNaN(Number.parseInt(option))) && option >= minAllowed && option <= maxAllowed;
}

function printReceipt(orderSummary) {
  const productsInOrder = orderSummary.products;
  const total = orderSummary.total;

  console.log('=== Receipt ===');

  // Print the products in the order
  productsInOrder.forEach((product) => {
    const lineItem = `${product.name} @ ${formatCurrency(product.price)} x ${product.quantity} = ${formatCurrency(product.price * product.quantity)}`
    console.log(lineItem);
  });

  // Print the total price
  console.log(`Total: ${formatCurrency(total)}`);
  console.log('===============');
}