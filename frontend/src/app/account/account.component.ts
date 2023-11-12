import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {UserInfo} from "../models/userInfo.model";
import {ProductService} from "../services/product.service";
import {PaymentInfo} from "../models/paymentInfo.model";
import {User} from "../models/user.model";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit
  // implements OnInit
{
  userId!: number;
  username: string = '';
  // @ts-ignore
  infoId: number;
  // @ts-ignore
  paymentId: number;
  userInfo: any;
  // @ts-ignore
  updatedUserInfo: UserInfo;
  editMode: boolean = false;
  userPayment: any;
  updatedUserPayment!: PaymentInfo;
  paymentEditMode = false;
  isDropdownOpen: boolean = false;
  selectedSize: string = '';
  hasAdminRole: boolean = false; // Initialize the admin role flag
  user: User = new User();

  constructor(private route: ActivatedRoute, private http: HttpClient, private productService: ProductService) {
  }

  ngOnInit() {

    this.route.params.subscribe(params => {
      // Retrieve the user ID and info ID from the route parameters
      this.userId = +params['userId'];
      this.infoId = +params['infoId'];
      this.paymentId = +params['paymentId'];
      console.log('Route Params:', params);
      console.log('User ID:', this.userId);
      console.log('Info ID:', this.infoId);
      console.log('Info ID:', this.paymentId);
      this.fetchUsername(this.userId);


      // Check if infoId is a valid number, if not, set it to a default value or handle it as desired
      if (isNaN(this.infoId)) {
        // Set a default value for infoId or handle it accordingly
        this.infoId = 0;
      }
    });

    this.loadUserInfo(this.userId);
    this.loadUserPayment(this.userId);


  }


  getUserInfo(userId: number): Observable<UserInfo> {
    const url = `http://localhost:8080/users/userinfo/${userId}`;
    return this.http.get<UserInfo>(url);
  }


  loadUserInfo(userId: number): void {
    this.getUserInfo(userId).subscribe(
      (userInfo: UserInfo) => {
        this.userInfo = userInfo;
        this.infoId = userInfo.id; // Assign the value of infoId from the user information
        console.log('User Info:', userInfo);
      },
      (error: any) => {
        console.error('Error fetching User Info:', error);
      }
    );
  }

  //

  saveUserInfo(): void {
    const userId = this.userId;
    const infoId = this.infoId;

    this.http
      .put(`http://localhost:8080/users/public/${userId}/userinfo/${infoId}`, this.userInfo)
      .subscribe(
        (response) => {
          // Handle the successful update
          console.log('User Info updated:', response);
          this.updatedUserInfo = response as UserInfo;
          this.editMode = false; // Disable edit mode after successful update
        },
        (error) => {
          // Handle the error response
          console.error('Error updating User Info:', error);
        }
      );
  }


  cancelEdit(): void {
    this.editMode = false; // Disable edit mode
  }


  getUserPayment(userId: number): Observable<PaymentInfo> {
    const url = `http://localhost:8080/users/userpayment/${userId}`;
    return this.http.get<PaymentInfo>(url);
  }

  loadUserPayment(userId: number): void {
    this.getUserPayment(userId).subscribe(
      (userPayment: PaymentInfo) => {
        this.userPayment = userPayment;
        this.userPayment.expiry = new Date(userPayment.expiry).toISOString().substring(0, 10); // Format the date
        this.paymentId = userPayment.id; // Assign the value of paymentId from the user payment information
        console.log('User Payment:', userPayment);
      },
      (error: any) => {
        console.error('Error fetching User Payment:', error);
      }
    );
  }

  saveUserPayment(): void {
    const userId = this.userId;
    const paymentId = this.userPayment.id;

    // Check if paymentId is a valid number, if not, set it to a default value or handle it as desired


    this.http
      .put(`http://localhost:8080/users/public/${userId}/userpayment/${paymentId}`, this.userPayment)
      .subscribe(
        (response) => {
          console.log('User Payment updated:', response);
          this.updatedUserPayment = response as PaymentInfo;
          this.paymentEditMode = false;
        },
        (error) => {
          console.error('Error updating User Payment:', error);
        }
      );


  }

  cancelPaymentEdit(): void {
    this.paymentEditMode = false;
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  logout() {
    // Handle logout logic here
    console.log('Logout action');
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


}
