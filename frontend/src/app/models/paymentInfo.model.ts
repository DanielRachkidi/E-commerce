import {User} from "./user.model";

export class PaymentInfo {
  id: number = 0;
  payment_type: string='';
  provider: string='';
  accountno: number=0;
  expiry!:Date ;
  user?: User;
}
