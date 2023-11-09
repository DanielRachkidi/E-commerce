import {User} from "./user.model";

export class UserInfo {
  id: number = 0;
  address: string ='';
  city: string='';
  code_postal: number =0;
  country: string ='';
  user?: User;
}
