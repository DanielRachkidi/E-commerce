import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Product} from "../product/product.component";
import {HomeComponent} from "../home/home.component";
import {CartService} from "../cart.service";
import {ProductService} from "../services/product.service";
import {SearchService} from "../services/search.service";
import {User} from "../models/user.model";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  // @ts-ignore
  productId: number;
  product: Product = new Product();
  // @ts-ignore
  userId: number;
  username: string | null = null;
  isDropdownOpen: boolean = false;
  showAdditionalInfo = false;
  showPanel: boolean = false;
  addedProducts: { id: number, product: Product }[] = [];
  quantity: number;
  @Input() searchTerm: string = '';
  selectedSize: string = '';
  filteredProducts: Product[] = [];
  isSearchInputFocused = false;
  getProducts: any[] = [];
  user:User = new User();
  hasAdminRole:boolean = false;
  toggleProductOptions: boolean = false;

  constructor(private route: ActivatedRoute, private http: HttpClient,
              public cartService: CartService, private productService: ProductService,
              private searchService: SearchService) {
    this.quantity = 1;
    this.addedProducts = [];

  }

  //for not going to negative or zero in the input of quantity
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
      this.productId = +params['productId'];
      this.fetchProductDetails(this.productId);
    });

    this.route.params.subscribe(params => {
      this.userId = +params['userId'];
      console.log('User ID:', this.userId);
      // Call your fetchUsername or any other logic using the userId
      this.fetchUsername(this.userId);
    });

    this.productService.addSelectedProduct(this.product);

    this.http.get<any[]>('http://localhost:8080/api/product/list')
      .subscribe((data: any[]) => {
        this.getProducts = data;

      });
  }


  fetchProductDetails(productId: number): void {
    // Make an HTTP request to fetch the product details based on the productId
    // Replace the URL and any necessary headers or parameters with your actual implementation
    this.http.get(`http://localhost:8080/api/product/${productId}`).subscribe(
      (response: any) => {
        // Assign the fetched product details to the 'product' property
        this.product = response;
      },
    );
  }


  addToBag(): void {
    const existingProduct = this.cartService.getAddedProducts().find(item => item.id === this.product.id);
    if (existingProduct) {


      console.log('Product already added to bag');
    } else {
      this.cartService.addProduct(this.product.id, this.product);
      this.cartService.updateProductQuantity(this.product.id, this.quantity);
      this.quantity = 1;
      this.showPanel = true;
    }
  }



  removeProduct(productId: number): void {
    this.cartService.removeProduct(productId);
  }


  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  logout() {
    // Handle logout logic here
    console.log('Logout action');
  }

  togglePanel(): void {
    this.showPanel = !this.showPanel;
  }

  updateQuantity(productId: number, quantity: number) {
    const addedProduct = this.cartService.getAddedProducts().find(product => product.id === productId);
    if (addedProduct) {
      addedProduct.product.quantity = quantity;
      this.cartService.updateProductQuantity(productId, quantity);
    }
  }

  // to be connected to page and the panel of the product add it in the panel when changing the quantity
  updateQuantityInCart(productId: number, quantity: number) {
    this.cartService.updateQuantityInCart(productId, quantity);

    // Update the quantity for each product in the cart
    this.cartService.getAddedProducts().forEach(item => {
      if (item.product.id === productId) {
        item.quantity = quantity;
      }
    });
  }


  fetchUsername(userId: number) {
    // Make an HTTP request to fetch the user based on the userId
    // Replace the URL and any necessary headers or parameters with your actual implementation
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



  calculateTotalPrice(): number {
    let totalPrice = 0;
    for (const addedProducts of this.cartService.getAddedProducts()) {
      totalPrice += addedProducts.product.price * addedProducts.quantity;
    }
    return totalPrice;
  }

}
