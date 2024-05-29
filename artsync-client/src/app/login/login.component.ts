import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
import { Utilisateur } from '../models/utilisateur.model';
import { Subscription } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private subscriptions: Subscription[] = [];
  
  constructor(private router: Router, 
    private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    if(this.authenticationService.isLoggedIn()){
      this.router.navigateByUrl('/generalView');
    }else{
      this.router.navigateByUrl('/login');
    }
  }

  onLogin(user : Utilisateur): void {
    console.log(user);
    this.subscriptions.push(
      this.authenticationService.login(user).subscribe({
        next: (response: HttpResponse<Utilisateur>) => {
          const token = response.headers.get("Jwt-Token");
          this.authenticationService.saveToken(token!);
          this.authenticationService.addUserToLocalCache(response.body!);
          this.router.navigateByUrl('/generalView');
        },
        error: (errorResponse: HttpErrorResponse) => {
          console.log(errorResponse);
        }
      })
    )
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
