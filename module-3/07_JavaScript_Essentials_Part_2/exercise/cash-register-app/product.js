import { formatCurrency } from './util.js';

function Product(id, name, price, sku, inventory = 0) {
  this.id = id;
  this.name = name;
  this.price = price;
  this.sku = sku;
  Object.freeze(this.id);
  Object.freeze(this.name);
  Object.freeze(this.price);
  Object.freeze(this.sku);

  this.inventory = inventory;
}

Product.prototype.toString = function () {
  return `Name: ${this.name} (Product ID: ${this.id})\n  Price: ${formatCurrency(this.price)}\n  Available: ${this.inventory}`;
}

const productsDataset = [
  new Product(1, 'Concert T-Shirt', 24.99, 'CT-001', 100),
  new Product(2, 'Band Poster', 14.99, 'BP-002', 200),
  new Product(3, 'Live Album CD', 24.99, 'CD-003', 150),
  new Product(4, 'Signed Vinyl Record', 49.99, 'VR-004', 50),
  new Product(5, 'LED Wristband', 6.99, 'LB-005', 500),
  new Product(6, 'Concert Hoodie', 44.99, 'CH-006', 75),
  new Product(7, 'Tour Program', 19.99, 'TP-007', 80),
  new Product(8, 'Commemorative Ticket', 19.99, 'CT-008', 300),
  new Product(9, 'Concert Tote Bag', 24.99, 'TB-009', 120),
  new Product(10, 'Exclusive Backstage Pass', 99.99, 'BP-010', 20)
];

function getProducts() {
  return productsDataset;
}

export { Product, getProducts };
