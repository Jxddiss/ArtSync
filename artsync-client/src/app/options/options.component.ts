import { Component } from '@angular/core';
import { AuthenticationService } from '../service/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-options',
  templateUrl: './options.component.html',
  styleUrl: './options.component.css'
})
export class OptionsComponent {
    constructor(private authenticationService : AuthenticationService, private router : Router){}

    onLogout(){
      this.authenticationService.logOut();
      this.router.navigate(['/login']);
    }
}
