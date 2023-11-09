import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {ProductService} from "../services/product.service";


export class Product {
  id: number = 0;
  name: string = '';
  price: number = 0;
  quantity: number = 0;
  size: string = '';
  gender: string = '';


}

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  userId: number = 0;
  name: string = "";
  price: number = 0.00;
  quantity: number = 0;
  size: string = "";
  gender: string = "";
  products: any[] = [];
  updateProductForm!: FormGroup;
  updateProduct!: Product;
  updateModalVisible = false;
  displayedColumns: string[] = ['name', 'price', 'quantity', 'size', 'gender'];
  dataSource = new MatTableDataSource<Product>([]);

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  @ViewChild(MatSort)
  sort!: MatSort;

  constructor(private router: Router, private http: HttpClient, private fb: FormBuilder, private route: ActivatedRoute,
              private productService: ProductService) {
  }


  ngOnInit(): void {
    this.productService.fetchProductList().subscribe((data: Product[]) => {
      this.products = data;
      this.dataSource = new MatTableDataSource<Product>(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;


    });



    this.updateProductForm = this.fb.group({
      name: ['', Validators.required],
      price: ['', Validators.required],
      quantity: ['', Validators.required],
      size: ['', Validators.required],
      gender: ['', Validators.required]
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

  save() {
    let bodyData = {
      id: this.userId,
      name: this.name,
      price: this.price,
      quantity: this.quantity,
      size: this.size,
      gender: this.gender,
    };

    this.productService.addProduct(bodyData).subscribe(
      (resultData: any) => {
        console.log(resultData);
        this.ngOnInit();
      },
    );
  }

  deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe(
      () => {
        console.log(`Product with id ${id} deleted successfully.`);
        this.ngOnInit();
      },
      (error) => {
        console.error(`Error deleting product with id ${id}: ${error.message}`);
      }
    );
  }


  closeUpdateModal() {
    this.updateProductForm.reset();
    this.updateModalVisible = false;
  }


  updateProductData() {

    const updatedProduct = {
      ...this.updateProduct,
      ...this.updateProductForm.value
    };
    this.productService.updateProduct(updatedProduct).subscribe(
      (updatedProduct: Product) => {
        console.log(`Product with id ${updatedProduct.id} updated successfully.`);
        this.closeUpdateModal();
        this.ngOnInit();

      },
      (error) => {
        console.error(`Error updating product with id ${this.updateProduct.id}: ${error.message}`);
      }
    );
  }

  showUpdateModal(product: Product) {

    this.updateProduct = product;
    this.updateProductForm.patchValue({
      name: product.name,
      price: product.price,
      quantity: product.quantity,
      size: product.size,
      gender: product.gender
    });

    this.updateModalVisible = true;
  }


  protected readonly Product = Product;
}
