import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {SessionService} from "../services/SessionService";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  userId: number=0;

  username: string = "";
  password: string = "";
  isUsernameMissing: boolean = false;
  isPasswordMissing: boolean = false;
  showPassword: boolean = false;

  isUsernameIncorrect: boolean = false;
  isPasswordIncorrect: boolean = false;
  passwordErrorMessage: String='';
  constructor(private router: Router, private http: HttpClient, private snackBar: MatSnackBar,
              private sessionService: SessionService) {
  }

// method to log in the homepage
  Login() {
    this.isUsernameMissing = this.username.trim() === '';
    this.isPasswordMissing = this.password.trim() === '';

    // if (this.password !== 'correct_password') {
    //   this.isPasswordIncorrect = true;
    //   this.passwordErrorMessage = 'Incorrect password';
    // } else {
    //   this.isPasswordIncorrect = false;
    //   this.passwordErrorMessage = '';
    // }

    if (!this.isUsernameMissing && !this.isPasswordMissing && !this.isUsernameIncorrect && !this.isPasswordIncorrect) {
      let bodyData = {
        username: this.username,
        password: this.password,
      };

      if (!this.isUsernameMissing && !this.isPasswordMissing && !this.isUsernameIncorrect && !this.isPasswordIncorrect) {
        let bodyData = {
          username: this.username,
          password: this.password,
        };

        this.http.post("http://localhost:8080/api/user/logins", bodyData,  { observe: 'response' }).subscribe(
          (response: any) => {
            console.log(response);
            console.log(this.username);
            console.log(this.password);

            this.snackBar.open('Login successful', 'Close', {
              duration: 3000,
              horizontalPosition: 'left',
              verticalPosition: 'top'
            });

            // this.userId = response.id; // Retrieve the userId from the response
            this.userId = response.body.id;
            const token = response.headers.get('Authorization');

            // Store the session information in localStorage
            localStorage.setItem('token', token);
            localStorage.setItem('userId', this.userId.toString());
            console.log('Token stored:', token);
            console.log('UserId stored:', this.userId);


            setTimeout(() => {
              this.router.navigate(['/home', this.userId]);
            }, 4000);
          },
          (error: any) => {
            console.log(error);
          }
        );
      }
    }
  }


      togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

}
