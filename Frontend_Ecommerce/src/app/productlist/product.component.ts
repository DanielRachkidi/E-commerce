import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Product} from "../product/product.component";
import {User} from "../models/user.model";
@Component({
  selector: 'app-productlist',
  templateUrl: './productlist.component.html',
  styleUrls: ['./productlist.component.css']
})




export class ProductlistComponent implements OnInit {
  user: User = new User();

  userId: number =0;

  products: any[] = [];

  displayedColumns: string[] = ['name', 'price', 'gender'];
  dataSource = new MatTableDataSource<any>([]);
  getProducts: any[] = [];

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  @ViewChild(MatSort)
  sort!: MatSort;
  constructor(private router: Router, private http: HttpClient, private route :ActivatedRoute) {
  }


  ngOnInit(): void {

        this.http.get<any[]>('http://localhost:8080/api/product/list')
      .subscribe((data: any[]) => {
        this.products = data;

        this.dataSource = new MatTableDataSource<Product>(data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;

      });


    const gender = this.route.snapshot.data['gender']; // Assuming you set the gender in the route data

    let apiEndpoint: string;
    if (gender === 'man') {
      apiEndpoint = 'http://localhost:8080/api/product/lists/man';
    } else if (gender === 'women') {
      apiEndpoint = 'http://localhost:8080/api/product/lists/women';
    }

    // @ts-ignore
    this.http.get<any[]>(apiEndpoint, { params: { gender } }).subscribe((data: any[]) => {
      this.products = data;

      // Do the common operations for both men and women products
      this.getProducts = data;
      // ...
    });

    this.route.params.subscribe(params => {
      this.userId = +params['userId'];
      console.log('User ID:', this.userId);

    });
  }



  sortData(event: { active: any; direction: any; }) {
    const sort = event.active;
    const direction = event.direction;
    // this.dataSource.sorting
  }

  // fetchUsername(userId: number) {
  //   // Make an HTTP request to fetch the username based on the userId
  //   // Replace the URL and any necessary headers or parameters with your actual implementation
  //   this.http.get(`http://localhost:8080/admin/admin/${userId}`).subscribe(
  //     (response: any) => {
  //       // Assign the fetched username to the 'username' property
  //       this.userId = response.userId;
  //     },
  //     error => {
  //       console.error('Error:', error);
  //     }
  //   );
  // }

}
