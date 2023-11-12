import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from "../models/user.model";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Product} from "../product/product.component";
import {CartService} from "../cart.service";
import {SearchService} from "../services/search.service";

@Component({
  selector: 'app-product-man',
  templateUrl: './product-man.component.html',
  styleUrls: ['./product-man.component.css']
})
export class ProductManComponent implements OnInit {
  userId: number=0;
  username: string | null = null;
  // @ts-ignore
  quantity: number
  user: User = new User();
  randomProducts: any[] = [];
  products: any[] = [];
  isDropdownOpen: boolean = false;
  showPanel: boolean = false;
  addedProducts: { id: number, product: Product, quantity: number }[] = [];
  searchTerm: string = '';
  filteredProducts: Product[] = [];
  isSearchInputFocused = false;
  getProducts: any[] = [];
  displayedColumns: string[] = ['name', 'price', 'gender'];
  dataSource = new MatTableDataSource<any>([]);
  toggleProductOptions: boolean = false;

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  @ViewChild(MatSort)
  sort!: MatSort;


  constructor(private router: Router, private http: HttpClient, private route :ActivatedRoute,
              public cartService: CartService, private searchService: SearchService,
            ) {
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
      console.log('User ID:', this.userId);
      this.fetchUsername(this.userId);
    });

    this.http.get<any[]>('http://localhost:8080/api/product/lists/man', { params: { gender:  'man' } })
      .subscribe((data: any[]) => {
        this.products = data;

        this.dataSource = new MatTableDataSource<Product>(data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;

        this.randomProducts = this.getRandomProducts(this.products, 4);


      });

//  the list of product in rhe search box
    this.http.get<any[]>('http://localhost:8080/api/product/list')
      .subscribe((data: any[]) => {
        this.getProducts = data;

      });





  }




  sortData(event: { active: any; direction: any; }) {
    const sort = event.active;
    const direction = event.direction;
    // this.dataSource.sorting
  }



  getRandomProducts(products: any[], count: number): any[] {

    const shuffledProducts = [...products].sort(() => Math.random() - 0.5);

    return shuffledProducts.slice(0, count);
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
  hasAdminRole:boolean = false;

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
    for (const addedProducts of this.cartService.getAddedProducts()) {
      totalPrice += addedProducts.product.price * addedProducts.quantity;
    }
    return totalPrice;
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

}
