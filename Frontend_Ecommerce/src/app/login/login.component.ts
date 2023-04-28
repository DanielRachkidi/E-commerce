import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  username: string ="";
  password: string ="";


  constructor(private router: Router,private http: HttpClient) {}


  Login() {
    console.log(this.username);
    console.log(this.password);
    let bodyData = {
      username: this.username,
      password: this.password,
    };
    this.http.post("http://localhost:8080/api/public/logins", bodyData).subscribe(  (resultData: any) => {
      console.log(resultData);
      console.log(this.username)
      console.log(this.password)
      if (resultData.message == "User not exists") {
        alert("User not exists");
      } else if(resultData.message === this.username) {
        alert("Login failed");
        this.router.navigateByUrl('/home');
      }
      else {
        alert("Login Success");
        this.router.navigateByUrl('/home');

      }
    });
  }

}
