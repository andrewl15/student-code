import { CashRegister } from './cashRegister.js';
import { getSales } from './sale.js';
import { getProducts } from './product.js';
import { should as _should, expect, assert } from 'chai';
const should = _should();

describe('Exercises - JavaScript Essentials Part 2', () => {
  describe('CashRegister constructor tests', () => {
    it('should have a products property', () => {
      const cashRegister = new CashRegister();
      cashRegister.should.have.property('allProducts', getProducts(), 'The CashRegister object should have an allProducts property and should be initially set to the products dataset');
    });

    it('should have an order property', () => {
      const cashRegister = new CashRegister();
      // .deep.property is used to compare the array values of the currentOrder property, not the reference (because [] !== [])
      cashRegister.should.have.deep.property('currentOrder', [], 'The CashRegister object should have a currentOrder property that is initially set to an empty array');
    });
  });

  describe('addProductToOrder() tests', () => {
    it('should add a product to the order if valid and in stock', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.addProductToOrder).to.be.a('function', 'CashRegister should have an addProductToOrder() function');
      const returnValue = cashRegister.addProductToOrder(1, 1);
      returnValue.should.equal(true, 'addProductToOrder should return true when a product is successfully added to the order');
      cashRegister.currentOrder.length.should.equal(1, 'The order should contain one product because the product was successfully added');
    });

    it('should not add a product to order if there is insufficient inventory', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.addProductToOrder).to.be.a('function', 'CashRegister should have an addProductToOrder() function');
      const returnValue = cashRegister.addProductToOrder(1, 999999);
      returnValue.should.equal(false, 'addProductToOrder should return false when there is insufficient inventory');
      cashRegister.currentOrder.length.should.equal(0, 'The order should not contain product because there is insufficient inventory');
    });

    it('should not add a product to the order if the product is not valid', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.addProductToOrder).to.be.a('function', 'CashRegister should have an addProductToOrder() function');
      const returnValue = cashRegister.addProductToOrder(999999, 1);
      returnValue.should.equal(false, 'addProductToOrder should return false when a product is not valid');
      cashRegister.currentOrder.length.should.equal(0, 'The order should not contain product because the product is not valid');
    });

    it('should update product inventory when a product is added to order', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.addProductToOrder).to.be.a('function', 'CashRegister should have an addProductToOrder() function');
      const productInStock = getProducts().find(p => p.id === 1);
      const preAddInventory = productInStock.inventory;
      cashRegister.addProductToOrder(1, 1);
      productInStock.inventory.should.equal(preAddInventory - 1, 'The product inventory should be updated when it is added to the order');
    });

    it('should add the product to the order with the correct quantity', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.addProductToOrder).to.be.a('function', 'CashRegister should have an addProductToOrder() function');
      cashRegister.addProductToOrder(1, 5);
      const productInOrder = cashRegister.currentOrder.find(p => p.id === 1);
      productInOrder.quantity.should.equal(5, 'The product should be added to the order with the correct quantity');
    });

    it('should not add a quantity to the product in the product list', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.addProductToOrder).to.be.a('function', 'CashRegister should have an addProductToOrder() function');
      cashRegister.addProductToOrder(1, 5);
      const product = getProducts().find(p => p.id === 1);
      should.not.exist(product.quantity, 'The product in the product list should not be updated with a quantity');
    });

    it('should handle multiple products in the order', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.addProductToOrder).to.be.a('function', 'CashRegister should have an addProductToOrder() function');
      cashRegister.addProductToOrder(1, 1);
      cashRegister.addProductToOrder(2, 2);
      cashRegister.addProductToOrder(3, 3);
      cashRegister.currentOrder.length.should.equal(3, 'The order should contain three separate products');
    });

    it('should update quantity of product in order instead of adding a new one', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.addProductToOrder).to.be.a('function', 'CashRegister should have an addProductToOrder() function');
      cashRegister.addProductToOrder(1, 1);
      cashRegister.addProductToOrder(1, 1);
      cashRegister.currentOrder.length.should.equal(1, 'The order should contain one product when adding the same product twice');
      const productInOrder = cashRegister.currentOrder.find(p => p.id === 1);
      productInOrder.quantity.should.equal(2, 'The product quantity should be updated instead of adding a new product');
    });
  });

  describe('removeProductFromOrder() tests', () => {
    it('should remove a product from the order if it exists', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.removeProductFromOrder).to.be.a('function', 'CashRegister should have a removeProductFromOrder() function');
      cashRegister.currentOrder.push({ id: 1, name: 'Test Product', price: 9.99, sku: 'TEST-SKU', inventory: 10, quantity: 1 });
      const returnValue = cashRegister.removeProductFromOrder(1);
      returnValue.should.equal(true, 'removeProductFromOrder should return true when a product is successfully removed from the order');
      cashRegister.currentOrder.length.should.equal(0, 'The order should be empty because the product was successfully removed');
    });

    it('should not remove other products from the order', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.removeProductFromOrder).to.be.a('function', 'CashRegister should have a removeProductFromOrder() function');
      cashRegister.currentOrder.push({ id: 1, name: 'Test Product 1', price: 9.99, sku: 'TEST-SKU-1', inventory: 10, quantity: 1 });
      cashRegister.currentOrder.push({ id: 2, name: 'Test Product 2', price: 19.99, sku: 'TEST-SKU-2', inventory: 5, quantity: 2 });
      cashRegister.removeProductFromOrder(1);
      cashRegister.currentOrder.length.should.equal(1, 'The order should contain one product after removing a product');
      const productInOrder = cashRegister.currentOrder.find(p => p.id === 2);
      assert.exists(productInOrder, 'The product that was not removed should still be in the order');
    });

    it('should not remove a product from the order if it does not exist', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.removeProductFromOrder).to.be.a('function', 'CashRegister should have a removeProductFromOrder() function');
      const returnValue = cashRegister.removeProductFromOrder(1);
      returnValue.should.equal(false, 'removeProductFromOrder should return false when a product does not exist in the order');
    });

    it('should update product inventory when a product is removed from order', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.removeProductFromOrder).to.be.a('function', 'CashRegister should have a removeProductFromOrder() function');
      const productInStock = getProducts().find(p => p.id === 1);
      const preAddInventory = productInStock.inventory;
      cashRegister.currentOrder.push({ ...productInStock, quantity: 1 }); // manually add the product to the order so we don't rely on addProductToOrder()
      cashRegister.removeProductFromOrder(1);
      // preAddInventory + 1 because we add the product directly to the order above and the inventory doesn't get adjusted until it is removed
      productInStock.inventory.should.equal(preAddInventory + 1, 'The product inventory should be updated when it is removed from the order');
    });
  });

  describe('calculateTotal() tests', () => {
    it('should calculate the total price of the order', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.calculateTotal).to.be.a('function', 'CashRegister should have a calculateTotal() function');
      cashRegister.currentOrder.push({ id: 1, name: 'Test Product 1', price: 9.99, sku: 'TEST-SKU-1', inventory: 10, quantity: 1 });
      cashRegister.currentOrder.push({ id: 2, name: 'Test Product 2', price: 19.99, sku: 'TEST-SKU-2', inventory: 5, quantity: 2 });
      cashRegister.currentOrder.push({ id: 3, name: 'Test Product 3', price: 29.99, sku: 'TEST-SKU-3', inventory: 3, quantity: 3 });
      const total = cashRegister.calculateTotal();
      total.should.equal(9.99 + (19.99 * 2) + (29.99 * 3), 'The total price of the order should be the sum of the subtotals of each product in the order');
    });

    it('should return 0 if the order is empty', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.calculateTotal).to.be.a('function', 'CashRegister should have a calculateTotal() function');
      const total = cashRegister.calculateTotal();
      total.should.equal(0, 'The total price of the order should be 0 if the order is empty');
    });
  });

  describe('getOrderSummary() tests', () => {
    it('should return an object with properties named products and total', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.getOrderSummary).to.be.a('function', 'CashRegister should have a getOrderSummary() function');
      const orderSummary = cashRegister.getOrderSummary();
      expect(orderSummary.products).to.be.an('array', 'getOrderSummary should return an object with a property named products');
      expect(orderSummary.total).to.be.a('number', 'getOrderSummary should return an object with a property named total');
    });

    it('should return the products in the order with the correct quantity', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.getOrderSummary).to.be.a('function', 'CashRegister should have a getOrderSummary() function');
      cashRegister.currentOrder.push({ id: 1, name: 'Test Product 1', price: 9.99, sku: 'TEST-SKU-1', inventory: 10, quantity: 1 });
      cashRegister.currentOrder.push({ id: 2, name: 'Test Product 2', price: 19.99, sku: 'TEST-SKU-2', inventory: 5, quantity: 2 });
      const orderSummary = cashRegister.getOrderSummary();
      expect(orderSummary.products).to.be.an('array', 'getOrderSummary should return an object with a property named products');

      const products = orderSummary.products;
      products.length.should.equal(2, 'getOrderSummary should return the products in the order');
      const product1 = products.find(p => p.id === 1);
      product1.quantity.should.equal(1, 'getOrderSummary should return the products in the order with the correct quantity');
      const product2 = products.find(p => p.id === 2);
      product2.quantity.should.equal(2, 'getOrderSummary should return the products in the order with the correct quantity');
    });

    it('should return the total price of the order', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.getOrderSummary).to.be.a('function', 'CashRegister should have a getOrderSummary() function');
      cashRegister.currentOrder.push({ id: 1, name: 'Test Product 1', price: 9.99, sku: 'TEST-SKU-1', inventory: 10, quantity: 1 });
      cashRegister.currentOrder.push({ id: 2, name: 'Test Product 2', price: 19.99, sku: 'TEST-SKU-2', inventory: 5, quantity: 2 });
      const orderSummary = cashRegister.getOrderSummary();
      expect(orderSummary.total).to.be.a('number', 'getOrderSummary should return an object with a property named total');
      const total = orderSummary.total;
      total.should.equal(9.99 + (19.99 * 2), 'getOrderSummary should return the total price of the order');
    });

    it('should return an empty products array if the order is empty', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.getOrderSummary).to.be.a('function', 'CashRegister should have a getOrderSummary() function');
      const orderSummary = cashRegister.getOrderSummary();
      expect(orderSummary.products).to.be.an('array', 'getOrderSummary should return an object with a property named products');
      const products = orderSummary.products;
      products.length.should.equal(0, 'getOrderSummary should return an empty array if there are no products in order');
    });

    it('should return a total of 0 if the order is empty', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.getOrderSummary).to.be.a('function', 'CashRegister should have a getOrderSummary() function');
      const orderSummary = cashRegister.getOrderSummary();
      expect(orderSummary.total).to.be.a('number', 'getOrderSummary should return an object with a property named total');
      const total = orderSummary.total;
      total.should.equal(0, 'getOrderSummary should return a total of 0 if the order is empty');
    });
  });

  describe('ringUpSale() tests', () => {
    it('should add a sale to the sales record', () => {
      const preRingUpSales = getSales().length;
      const cashRegister = new CashRegister();
      expect(cashRegister.ringUpSale).to.be.a('function', 'CashRegister should have a ringUpSale() function');
      cashRegister.currentOrder.push({ id: 1, name: 'Test Product', price: 9.99, sku: 'TEST-SKU', inventory: 10, quantity: 1 });
      const paymentType = 'CASH';
      cashRegister.ringUpSale(paymentType);
      getSales().length.should.equal(preRingUpSales + 1, 'A sale should be added to the sales record when ringing up a sale');
    });

    it('should record a sale with the correct total', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.ringUpSale).to.be.a('function', 'CashRegister should have a ringUpSale() function');
      cashRegister.currentOrder.push({ id: 1, name: 'Test Product', price: 9.99, sku: 'TEST-SKU', inventory: 10, quantity: 1 });
      const paymentType = 'CASH';
      const total = cashRegister.calculateTotal();
      cashRegister.ringUpSale(paymentType);
      const sales = getSales();
      sales[sales.length - 1].total.should.equal(total, 'The sale should have the correct total');
    });

    it('should record a sale with the correct payment type', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.ringUpSale).to.be.a('function', 'CashRegister should have a ringUpSale() function');
      cashRegister.currentOrder.push({ id: 1, name: 'Test Product', price: 9.99, sku: 'TEST-SKU', inventory: 10, quantity: 1 });
      const paymentType = 'CASH';
      cashRegister.ringUpSale(paymentType);
      const sales = getSales();
      sales[sales.length - 1].paymentType.should.equal(paymentType, 'The sale should have the correct payment type');
    });

    it('should record a sale with the correct items', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.ringUpSale).to.be.a('function', 'CashRegister should have a ringUpSale() function');
      cashRegister.currentOrder.push({ id: 1, name: 'Test Product', price: 9.99, sku: 'TEST-SKU', inventory: 10, quantity: 1 });
      const paymentType = 'CASH';
      cashRegister.ringUpSale(paymentType);
      const sales = getSales();
      sales[sales.length - 1].items[0].productId.should.equal(1, 'The sale should have the correct items');
      sales[sales.length - 1].items[0].quantity.should.equal(1, 'The sale should have the correct items');
      sales[sales.length - 1].items[0].subTotal.should.equal(9.99, 'The sale should have the correct items');
    });
    it('should clear the current order', () => {
      const cashRegister = new CashRegister();
      expect(cashRegister.ringUpSale).to.be.a('function', 'CashRegister should have a ringUpSale() function');
      cashRegister.currentOrder.push({ id: 1, name: 'Test Product', price: 9.99, sku: 'TEST-SKU', inventory: 10, quantity: 1 });
      const paymentType = 'CASH';
      cashRegister.ringUpSale(paymentType);
      cashRegister.currentOrder.length.should.equal(0, 'The current order should be empty after ringing up the sale');
    });
  });
});