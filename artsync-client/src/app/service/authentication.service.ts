import { Injectable } from '@angular/core';
import { environment } from '../constants/environment.constant';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Utilisateur } from '../models/utilisateur.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private backend = environment.apiUrl
  private token: string | null = null
  private jwtHelper = new JwtHelperService();

  constructor(private httpClient:HttpClient) { }

  login(user : Utilisateur) : Observable<HttpResponse<Utilisateur>>{
    return this.httpClient.post<Utilisateur>(`${this.backend}/api/admin/login`,user,{observe: 'response'});
  }

  logOut() : void{
    this.token = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
  }

  saveToken(token : string) : void{
    this.token = token;
    localStorage.setItem('token', token);
  }

  isLoggedIn():boolean{
    this.loadToken();
    if(this.token != null && this.token !== ''){
      if(this.jwtHelper.decodeToken(this.token).sub != null || '' || undefined){
        if(!this.jwtHelper.isTokenExpired(this.token)){
          return true;
        }
      }
    }else{
      this.logOut();
      return false;
    }
    return false;
  }

  addUserToLocalCache(user : Utilisateur) : void{
    localStorage.setItem('user', JSON.stringify(user));
  }

  getUserFromLocalCache() : Utilisateur{
    return JSON.parse(localStorage.getItem('user')!);
  }

  loadToken() : void{
    this.token = localStorage.getItem('token');
  }

  getToken() : string | null{
    return this.token;
  }
}
