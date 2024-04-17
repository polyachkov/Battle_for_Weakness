import { Pipe, PipeTransform } from '@angular/core';
import {UInfo} from "../models/user-model";

@Pipe({
  name: 'filterUsers'
})
export class FilterUsersPipe implements PipeTransform {

  transform(users: UInfo[], search: string): UInfo[] {
    return users.filter(u => u.username.toLowerCase().includes(search.toLowerCase()));
  }

}
