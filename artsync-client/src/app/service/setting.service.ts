import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../constants/environment.constant';
import { Setting } from '../models/setting.model';

@Injectable({
  providedIn: 'root'
})
export class SettingService {
  private host_url = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }
  
  getSettings() {
    return this.httpClient.get<Setting>(`${this.host_url}/api/app-setting`);
  }

  updateSettings(setting: Setting) {
    return this.httpClient.put<Setting>(`${this.host_url}/api/app-setting`, setting);
  }
}
