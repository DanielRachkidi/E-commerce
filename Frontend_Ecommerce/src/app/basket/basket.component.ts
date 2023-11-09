import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductService} from '../services/product.service';
import {Product} from "../product/product.component";
import {ActivatedRoute} from "@angular/router";
import {CartService} from "../cart.service";
import {ProductDetailsComponent} from "../product-details/product-details.component";
import {SearchService} from "../services/search.service";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user.model";

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css']
})
export class BasketComponent implements OnInit {
  // @ts-ignore
  userId: number;
  username:string = '';
  // @ts-ignore
  quantity: number;

  addedProducts: { id: number, product: Product, quantity: number }[] = [];
// @ts-ignore
  productDetailComponent: ProductDetailsComponent = new ProductDetailsComponent();
  isDropdownOpen: boolean = false;
  showPanel: boolean = false;
  searchTerm: string = '';
  filteredProducts: Product[] = [];
  isSearchInputFocused = false;
  getProducts: any[] = [];
  @ViewChild(ProductDetailsComponent)
  productDetailsComponent!: ProductDetailsComponent;
  user:User = new User();
  hasAdminRole:boolean = false;
  toggleProductOptions: boolean = false;

  constructor(private route: ActivatedRoute, public cartService: CartService,
              private searchService: SearchService, private http: HttpClient) {

  }

  validateQuantity(): void {
    if (this.quantity < 1 || isNaN(this.quantity)) {
      this.quantity = 1;
    }
  }

  search(userId: number, productId: number): void {
    this.searchService.search(userId, productId);
  }

  filterProducts(): void {
    // @ts-ignore
    this.filteredProducts = this.getProducts.filter(product =>
      product.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  toggleSearchInputFocus(focused: boolean) {
    this.isSearchInputFocused = focused;
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['userId'];
      this.fetchUsername(this.userId)
    });

    this.addedProducts = this.cartService.getAddedProducts();

    this.http.get<any[]>('http://localhost:8080/api/product/list')
      .subscribe((data: any[]) => {
        this.getProducts = data;

      });
  }


  removeProduct(productId: number): void {
    this.cartService.removeProduct(productId);
  }

  updateQuantity(productId: number, quantity: number) {
    const addedProduct = this.cartService.getAddedProducts().find(product => product.id === productId);
    if (addedProduct) {
      addedProduct.product.quantity = quantity;
      this.cartService.updateProductQuantity(productId, quantity);
    }
  }

  calculateTotalPrice(): number {
    let totalPrice = 0;
    for (const addedProduct of this.addedProducts) {
      totalPrice += addedProduct.product.price * addedProduct.quantity;
    }
    return totalPrice;
  }


  fetchUsername(userId: number) {
    // Make an HTTP request to fetch the user based on the userId
    this.http.get<User>(`http://localhost:8080/admin/admin/${userId}`).subscribe(
      (response: User) => {
        // Assign the fetched user object to the 'user' property
        this.user = response;
        console.log('User Object:', this.user);

        // Check if the user object is defined and has the 'roles' property
        if (this.user && this.user.roles) {
          console.log('User Roles:', this.user.roles);

          // Check if the user has the admin role
          this.hasAdminRole = this.hasUserRole(this.user, 'admin');
          console.log('Has Admin Role:', this.hasAdminRole);
          this.username = this.user.username;
          console.log('Username:', this.username);

        } else {
          console.log('User Object is undefined or has no roles property');
        }
      },
      error => {
        console.error('Error:', error);
      }
    );
  }

  hasUserRole(user: User, role: string): boolean {
    // Check if the user object is defined and has the 'roles' property
    if (user && user.roles) {
      console.log('User Object:', user);
      console.log('User Roles:', user.roles);

      // Check if the user has the given role
      // @ts-ignore
      const hasRole = user.roles.some(r => r.roleName === role);
      console.log('Has Role:', hasRole);

      return hasRole;
    }

    console.log('User Object is undefined or has no roles property');
    return false; // Return false if the user object is undefined or has no 'roles' property
  }


  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  logout() {
    // Handle logout logic here
    console.log('Logout action');
  }


  searchInProductDetails() {
    // @ts-ignore
    this.productDetailsComponent.search();
  }


  protected readonly CartService = CartService;
}
