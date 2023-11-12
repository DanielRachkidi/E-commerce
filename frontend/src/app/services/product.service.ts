import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Product} from "../product/product.component";
import {Observable} from "rxjs";
import {UserInfo} from "../models/userInfo.model";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/product';
  selectedProducts: Product[] = [];

  constructor(private http: HttpClient) {}

  //return or get product from the database
  fetchProductList() {
    return this.http.get<Product[]>(`${this.baseUrl}/list`);
  }

  //insert or save the product that been add in the database
  addProduct(product: Product) {
    return this.http.post(`${this.baseUrl}/addproduct`, product);
  }

  // delete product
  deleteProduct(productId: number) {
    return this.http.delete(`${this.baseUrl}/delete/${productId}`);
  }

  // update product
  updateProduct(product: Product) {
    return this.http.put<Product>(`${this.baseUrl}/products/${product.id}`, product);
  }

  // add or push the product from the database in the specific place just like the basket that been created
  addSelectedProduct(product: Product): void {
    this.selectedProducts.push(product);
  }
}
