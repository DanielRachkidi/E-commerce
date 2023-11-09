import { HttpClient } from '@angular/common/http';
import { Component,OnInit } from '@angular/core';
import {Router} from "@angular/router";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import {User} from "../models/user.model";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent  {
  showPasswordRequirements: boolean = false;

  user: User = new User();
  userId: number=0;

  username: string ="";
  password: string ="";
  first_name: string ="";
  last_name: string ="";
  telephone: number= 0;


  constructor(private router: Router,private http: HttpClient, private snackBar: MatSnackBar )
  {

  }



  //method for creating a registration the client
  save()
  {

    let bodyData = {
      "username" : this.username,
      "password" : this.password,
      "first_name" : this.first_name,
      "last_name" : this.last_name,
      "telephone" : this.telephone
    };

    this.http.post("http://localhost:8080/users/public/signup", bodyData).subscribe(
      (resultData: any) => {
        console.log(resultData);
        console.log(this.username);
        console.log(this.password);

        this.snackBar.open('Register successful', 'Close', {
          duration: 6000,
          horizontalPosition: 'left',
          verticalPosition: 'top'
        });


        this.userId = resultData.id; // Retrieve the userId from the response


        setTimeout(() => {
          this.router.navigate(['/information', this.userId]);
        }, 4000);
      },
    );
  }


}
