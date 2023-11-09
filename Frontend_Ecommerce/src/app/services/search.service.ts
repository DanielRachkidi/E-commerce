import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable()
export class SearchService {
  searchTerm: string = '';

  constructor(private router: Router) {}


  // when filter the input box for the products click on it and will navigate of it page from the id of the product
  search(userId: number,productId: number): void {

    // Navigate to the product details page passing the product ID as a route parameter
    this.router.navigate(['/product-details', userId, productId]);
  }

}
