import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ProductComponent} from "./product/product.component";
import {ProductlistComponent} from "./productlist/product.component";
import {AccountComponent} from "./account/account.component";
import {InformationComponent} from "./information/information.component";
import {PaymentComponent} from "./payment/payment.component";
import {ProductDetailsComponent} from "./product-details/product-details.component";
import {ProductManComponent} from "./product-man/product-man.component";
import {ProductWomenComponent} from "./product-womem/product-women.component";
import {BasketComponent} from "./basket/basket.component";

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },

  {
    path: 'home/:userId',
    component: HomeComponent,

  },
  {

    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'addproduct/:userId',
    component: ProductComponent,
  },
  {
    path:'productlist/:userId',
    component: ProductlistComponent,

  },
  {
    path:'account/:userId',
    component: AccountComponent,
  },
  {
    path: 'information/:userId',
    component: InformationComponent,
  },

  {
    path:'payment/:userId',
    component: PaymentComponent
  },
  { path:'product-details/:userId/:productId',
    component: ProductDetailsComponent
  },
  {
    path:'product-man/:userId',
    component:ProductManComponent
  },
  {
    path:'product-women/:userId',
    component: ProductWomenComponent
  },

  {
    path:'basket/:userId',
    component:BasketComponent
  }

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
