import { Pipe, PipeTransform } from '@angular/core';
import {IUser} from "../models/user-model";

@Pipe({
  name: 'filterUsers'
})
export class FilterUsersPipe implements PipeTransform {

  transform(users: IUser[], search: string): IUser[] {
    return users.filter(u => u.username.toLowerCase().includes(search.toLowerCase()));
  }

}
