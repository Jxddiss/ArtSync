import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../constants/environment.constant';
import { Demande } from '../models/demande.model';

@Injectable({
  providedIn: 'root'
})
export class DemandeService {

  private host_url = environment.apiUrl

  constructor(private httpClient: HttpClient) { }

  getDemandes() {
    return this.httpClient.get<Demande[]>(this.host_url + '/api/demandes-admin');
  }

  deleteDemande(demande: Demande) {
    return this.httpClient.delete<any>(this.host_url + '/api/demandes-admin',{body:demande});
  }
}
