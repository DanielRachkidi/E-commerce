import { Injectable } from '@angular/core';
import { Product} from "./product/product.component";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private addedProducts: { id: number, product: Product, quantity: number }[] = [];

  constructor() {}

  // Get the array of added products in the cart
  getAddedProducts(): { id: number, product: Product, quantity: number }[] {
    return this.addedProducts;
  }

  // Add a product to the cart
  addProduct(id: number, product: Product): void {
    // // this will add  different prodcts in the cart
    // this.addedProducts.push({ id, product, quantity: 1 });
    // Check if the product already exists in the cart
    const existingProduct = this.addedProducts.find(item => item.id === id);
    if (existingProduct) {
      console.log('Product already added to the cart');
      return;
    }

    // If the product doesn't exist, add it to the cart
    const newProduct = { id, product, quantity: 1 };
    this.addedProducts.push(newProduct);
    console.log('Product added to the cart');
  }

  // Remove a product from the cart based on its ID
  removeProduct(id: number): void {
    const index = this.addedProducts.findIndex(item => item.id === id);
    if (index !== -1) {
      this.addedProducts.splice(index, 1);
    }
  }

  // Update the quantity of a product in the cart based on its ID
  updateProductQuantity(productId: number, quantity: number) {

    if (quantity <= 0) {
      console.log('Invalid quantity');
      return;
    }
    const existingProduct = this.addedProducts.find(item => item.id === productId);
    if (existingProduct) {
      existingProduct.quantity = quantity;
      console.log('Product quantity updated');
    } else {
      console.log('Product not found in the cart');
    }
  }

  // Update the quantity of a product in the cart based on its ID (alternative method)
  updateQuantityInCart(productId: number, quantity: number) {
    const item = this.addedProducts.find(item => item.product.id === productId);
    if (item) {
      item.quantity = quantity;
    }
  }

}
