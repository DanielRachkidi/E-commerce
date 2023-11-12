import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../models/user.model";
import {UserInfo} from "../models/userInfo.model";





@Component({
  selector: 'app-information',
  templateUrl: './information.component.html',
  styleUrls: ['./information.component.css']
})
export class InformationComponent {
  // @ts-ignore
  userId: number;
  userInfo: UserInfo = new UserInfo();


  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    // fetch the userId from the register
    this.route.params.subscribe(params => {
      // Retrieve the user ID from the route parameters
      this.userId = +params['userId'];
    });
  }
  //save or insert attribute of the UserInfo
  createUserInfo() {
    const userInfoData: UserInfo = {
      id: 0,
      address: this.userInfo.address,
      city: this.userInfo.city,
      code_postal: this.userInfo.code_postal,
      country: this.userInfo.country,
      user: new User()// Relation Many To One
    };



    this.http.post(`http://localhost:8080/users/public/${this.userId}/userinfo`, userInfoData).subscribe(
      (response: any) => {
        // Handle successful creation
        console.log('User info created:', response);

        setTimeout(() => {
          this.router.navigate(['/payment', this.userId]);
        }, 2000);
      },




    );
  }
}
