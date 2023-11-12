import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TranslateService} from '@ngx-translate/core';
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../models/user.model";
import {Product} from "../product/product.component";
import {CartService} from "../cart.service";
import {SearchService} from "../services/search.service";
import {SessionService} from "../services/SessionService";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  selectedLanguage: string = 'en';
  user: User = new User();
  getManProducts: any[] = [];
  getWomenProducts: any[] = [];
  manRandomProducts: any[] = [];
  womenRandomProducts: any[] = [];
  toggleProductOptions: boolean = false;


  // @ts-ignore
  userId: number;
  // @ts-ignore
  infoId: number;
  username: string | null = null;
  // @ts-ignore
  quantity: number
  isDropdownOpen: boolean = false;
  showPanel: boolean = false;
  // @ts-ignore
  featuredProducts: Product[];
  products: any[] = [];

  searchTerm: string = '';
  addedProducts: { id: number, product: Product, quantity: number }[] = [];
  filteredProducts: Product[] = []; // empty array of the product use it for the product filtering in search box
  isSearchInputFocused = false;
  hasAdminRole: boolean = false; // Initialize the admin role flag

  //method to select the role of the user from User class from specifying the role should be use
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


  constructor(private http: HttpClient, private route: ActivatedRoute, private translate: TranslateService,
              public cartService: CartService, private searchService: SearchService, private router: Router,
              private sessionService: SessionService) {

    this.translate.setDefaultLang(this.selectedLanguage);
    this.translate.use(this.selectedLanguage);
  }

  // method exception if negative
  validateQuantity(): void {
    if (this.quantity < 1 || isNaN(this.quantity)) {
      this.quantity = 1;
    }
  }



  //method for drop down the toggle of box that  have another  boxes inside it
  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }




  switchLanguage(lang: string) {
    this.selectedLanguage = lang; // Update the selected language

    this.translate.use(lang); // Set the active language
    console.log(this.selectedLanguage)
  }


  ngOnInit() {


    this.featuredProducts = [
      {
        id: 1,
        name: 'Product 1',
        price: 9.99,
        quantity: 10,
        size: 'XS',
        gender: 'man'
      },
      {
        id: 2,
        name: 'Product 2',
        price: 19.99,
        quantity: 5,
        size: 'XS',
        gender: 'man'
      },
      {
        id: 3,
        name: 'Product 3',
        price: 19.99,
        quantity: 5,
        size: 'XS',
        gender: 'man'
      },
      {
        id: 4,
        name: 'Product 4',
        price: 19.99,
        quantity: 5,
        size: 'XS',
        gender: 'man'
      },
    ];

    // Fetch man products
    this.http.get<any[]>('http://localhost:8080/api/product/lists/man', {params: {gender: 'man'}})
      .subscribe((data: any[]) => {
        this.getManProducts = data;


        const manProducts = this.getManProducts; // Create a separate array for man's products
        this.manRandomProducts = this.getRandomProducts(manProducts, 4); // fetch 4 random product of value man
      });

    // Fetch women products
    this.http.get<any[]>('http://localhost:8080/api/product/lists/women', {params: {gender: 'women'}})
      .subscribe((body: any[]) => {
        this.getWomenProducts = body;

        const womenProducts = this.getWomenProducts; // Create a separate array for women's products
        this.womenRandomProducts = this.getRandomProducts(womenProducts, 4); // fetch 4 random product of value women
      });

    // Retrieve the user ID from the route parameters and fetch the username
    this.route.params.subscribe(params => {
      // Retrieve the user ID from the route parameters
      this.userId = +params['userId'];
      console.log('Route Params:', params);
      console.log('User ID:', this.userId); // Check the value here
    // method from fetch user id and name with his role
      this.fetchUsername(this.userId);


    });
    // Retrieve the user ID from the route parameters and fetch the username
    this.route.params.subscribe(params => {
      // Retrieve the user ID from the route parameters
      this.infoId = +params['infoId'];
      console.log('Route Params:', params);
      console.log('User ID:', this.infoId); // Check the value here
    });

    // Fetch products in the search box
    //for the search  input box that will be added in the filterProducts() method
    this.http.get<any[]>('http://localhost:8080/api/product/list')
      .subscribe((data: any[]) => {
        this.products = data;

      });
  }

// search service for method that fetch product id
  search(userId: number, productId: number): void {
    this.searchService.search(userId, productId);
  }

  //method is responsible for filtering the products based on the search term entered by the user.
  filterProducts(): void {
    // @ts-ignore
    this.filteredProducts = this.products.filter(product =>
      product.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  // search box show the name of product
  toggleSearchInputFocus(focused: boolean) {
    this.isSearchInputFocused = focused;
  }

  fetchUsername(userId: number) {
    //fetch the user based on the userId
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


  logout() {
    // Handle logout logic here
    this.sessionService.clearSession();

    this.router.navigate(['/']);

    console.log('Logout action');
  }

// delete product from the basket or cart
  removeProduct(productId: number): void {
    this.cartService.removeProduct(productId);
  }

  //method to update  quantity that to be added in the bag by clicking the button add to bag
  updateQuantity(productId: number, quantity: number) {
    const addedProduct = this.cartService.getAddedProducts().find(product => product.id === productId);
    if (addedProduct) {
      addedProduct.product.quantity = quantity;
      this.cartService.updateProductQuantity(productId, quantity);
    }
  }

  //  calculate  the price from  the product attribute price and quantity
  calculateTotalPrice(): number {
    let totalPrice = 0;
    for (const addedProducts of this.cartService.getAddedProducts()) {
      totalPrice += addedProducts.product.price * addedProducts.quantity;
    }
    return totalPrice;
  }


  //method to shuffle the index number
  getRandomProducts(products: any[], count: number): any[] {

    const shuffledProducts = [...products].sort(() => Math.random() - 0.5);

    return shuffledProducts.slice(0, count);
  }


}



