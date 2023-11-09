import { Component } from '@angular/core';
import {UserInfo} from "../models/userInfo.model";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../models/user.model";
import {PaymentInfo} from "../models/paymentInfo.model";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {

  // @ts-ignore
  userId: number;
  paymentInfo: PaymentInfo = new PaymentInfo();

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    //fetch from the UserInfo connect it to the Register Page
    this.route.params.subscribe(params => {
      // Retrieve the user ID from the route parameters
      this.userId = +params['userId'];
    });
  }

  // insert or save method of the payment
  createPaymentInfo() {
    const paymentInfoData: PaymentInfo = {
      id: 0,
      payment_type: this.paymentInfo.payment_type,
      provider: this.paymentInfo.provider,
      accountno: this.paymentInfo.accountno,
      expiry: this.paymentInfo.expiry,
      user: new User() // Relation Many to One
    };

    console.log(paymentInfoData);
    this.http.post(`http://localhost:8080/users/public/${this.userId}/userpayment`, paymentInfoData).subscribe(
      (response: any) => {
        // Handle successful creation
        console.log('User payment created:', response);


        setTimeout(() => {
          this.router.navigate(['']);
        }, 2000);


      },




    );
  }
}
