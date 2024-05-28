import { Injectable } from '@angular/core';
import { USERS } from './mock-user';

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {

  getAllUsers() {
    return USERS;
  }
  getUserById(id: number) {
      return USERS.find(user => user.id === id);
  }
}
