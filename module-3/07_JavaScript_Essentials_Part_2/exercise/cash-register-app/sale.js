import { PAYMENT_TYPES } from './paymentType.js';

function Sale(items, total, paymentType) {
  this.items = items;
  this.total = total;
  this.paymentType = paymentType;
  Object.freeze(this);
}

const salesDataset = [
  new Sale([ { productId: 1, quantity: 1, subTotal: 24.99 } ], 24.99, PAYMENT_TYPES.CASH),
  new Sale([ { productId: 2, quantity: 2, subTotal: 29.98 } ], 29.98, PAYMENT_TYPES.CREDIT_CARD),
  new Sale([ { productId: 1, quantity: 1, subTotal: 24.99 }, { productId: 3, quantity: 1, subTotal: 24.99 } ], 49.98, PAYMENT_TYPES.CRYPTO),
  new Sale([ { productId: 1, quantity: 2, subTotal: 49.98 }, { productId: 4, quantity: 1, subTotal: 49.99 } ], 99.97, PAYMENT_TYPES.MOBILE_WALLET),
  new Sale([ { productId: 5, quantity: 3, subTotal: 20.97 } ], 20.97, PAYMENT_TYPES.CASH),
  new Sale([ { productId: 6, quantity: 1, subTotal: 44.99 } ], 44.99, PAYMENT_TYPES.CREDIT_CARD),
  new Sale([ { productId: 7, quantity: 1, subTotal: 19.99 } ], 19.99, PAYMENT_TYPES.CREDIT_CARD),
  new Sale([ { productId: 8, quantity: 2, subTotal: 39.98 } ], 39.98, PAYMENT_TYPES.CRYPTO),
  new Sale([ { productId: 6, quantity: 1, subTotal: 44.99 }, { productId: 9, quantity: 1, subTotal: 24.99 } ], 69.98, PAYMENT_TYPES.MOBILE_WALLET),
  new Sale([ { productId: 1, quantity: 2, subTotal: 49.98 }, { productId: 2, quantity: 1, subTotal: 14.99 }, { productId: 4, quantity: 1, subTotal: 49.99 }, { productId: 10, quantity: 2, subTotal: 199.98 } ], 314.94, PAYMENT_TYPES.CREDIT_CARD),
  new Sale([ { productId: 3, quantity: 1, subTotal: 24.99 }, { productId: 5, quantity: 1, subTotal: 6.99 }, { productId: 7, quantity: 1, subTotal: 19.99 }, { productId: 9, quantity: 1, subTotal: 24.99 } ], 76.96, PAYMENT_TYPES.CRYPTO),
  new Sale([ { productId: 2, quantity: 1, subTotal: 14.99 }, { productId: 4, quantity: 1, subTotal: 49.99 }, { productId: 6, quantity: 1, subTotal: 44.99 }, { productId: 8, quantity: 1, subTotal: 19.99 }, { productId: 10, quantity: 1, subTotal: 99.99 } ], 229.95, PAYMENT_TYPES.MOBILE_WALLET),
];

function getSales() {
  return salesDataset;
}

function getSalesTotal() {
  return salesDataset.reduce((total, sale) => total + sale.total, 0);
}

function getSalesTotalByPaymentType(paymentType) {
  return salesDataset.filter(sale => sale.paymentType === paymentType)
    .reduce((total, sale) => total + sale.total, 0);
}

/**
 * Records a sale to the sales dataset. The dataset contains the products sold (items), sale total, and payment type.
 * Those three pieces of information are used to instantiate a new Sale object, which is then added to the sales dataset.
 *
 * @param {Object[]} items data for the products sold in the sale (productId, quantity, and subTotal).
 * @param {Number} total the total amount of the sale.
 * @param {String} paymentType The payment type used for the sale. Must be one of PAYMENT_TYPES in paymentType.js.
 */
function addSale(items, total, paymentType) {
  const newSale = new Sale(items, total, paymentType);
  salesDataset.push(newSale);
}

export { Sale, getSales, getSalesTotal, getSalesTotalByPaymentType, addSale };
