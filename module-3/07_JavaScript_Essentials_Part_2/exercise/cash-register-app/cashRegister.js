import { getProducts } from "./product.js";
import { addSale } from "./sale.js";

function CashRegister(allProducts, currentOrder) {
    this.allProducts = getProducts();
    this.currentOrder = [];
};

CashRegister.prototype.addProductToOrder = function(productId, quantity) {
    const product = this.allProducts.find(product => product.id === productId);

    if(!product || product.inventory < quantity){return false;}

    const orderItem = this.currentOrder.find(item => item.id === productId);

    if(orderItem){
        orderItem.quantity += 1;
    }else {
        const newOrderItem = {
            id: product.id,
            name: product.name,
            price: product.price,
            sku: product.sku,
            quantity: quantity
        };
        this.currentOrder.push(newOrderItem);
    }


    product.inventory -= quantity;

    return true

}

CashRegister.prototype.removeProductFromOrder = function(productId){
    const orderItem = this.currentOrder.find(item => item.id === productId);
    if(orderItem){
        this.currentOrder.splice(this.currentOrder.indexOf(orderItem), 1);
        const product = this.allProducts.find(product => product.id === productId);
        product.inventory += orderItem.quantity;
        return true;
    } else {
        return false;
    }
}


CashRegister.prototype.calculateTotal = function(){
    const total = this.currentOrder.reduce((accumulator, order) => {
        return accumulator + order.price * order.quantity
    }, 0);
    return total;
}

CashRegister.prototype.getOrderSummary = function(){
    return {products: this.currentOrder, total: this.calculateTotal()};
}

CashRegister.prototype.ringUpSale = function(paymentType){
    const items = this.currentOrder.map(orderItem => {
        return {
            productId: orderItem.id,
            quantity: orderItem.quantity,
            subTotal: orderItem.price * orderItem.quantity
        }
    })
    addSale(items, this.calculateTotal(), paymentType);
    this.currentOrder = [];
}

//  new Sale([ { productId: 1, quantity: 1, subTotal: 24.99 } ], 24.99, PAYMENT_TYPES.CASH),

export { CashRegister };