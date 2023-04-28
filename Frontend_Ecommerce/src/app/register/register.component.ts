import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  username: string ="";
  password: string ="";
  first_name: string ="";
  last_name: string ="";
  telephone: number= 0;


  constructor(private http: HttpClient )
  {

  }
  save()
  {

    let bodyData = {
      "username" : this.username,
      "password" : this.password,
      "first_name" : this.first_name,
      "last_name" : this.last_name,
      "telephone" : this.telephone
    };
    this.http.post("http://localhost:8080/users/public/signup",bodyData,{responseType: 'text'}).subscribe((resultData: any)=>
    {
      console.log(resultData);
      alert("User Registered Successfully");

    });
  }

}
