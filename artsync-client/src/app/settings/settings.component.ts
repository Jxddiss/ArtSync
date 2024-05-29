import { Component } from '@angular/core';
import { Setting } from '../models/setting.model';
import { SettingService } from '../service/setting.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent {
  private _settings: Setting = {} as Setting;
  private _subscriptions: Subscription[] = [];

  constructor(private settingService: SettingService) { }

  ngOnInit(): void {
    this.getSettings();
  }

  ngOnDestroy(): void {
    this._subscriptions.forEach(sub => sub.unsubscribe());
  }

  getSettings(): void {
    this._subscriptions.push(
      this.settingService.getSettings()
              .subscribe(settings => this._settings = settings)
    );
  }

  onUpdateSettings(): void {
    this._subscriptions.push(
      this.settingService.updateSettings(this._settings)
              .subscribe(settings => this._settings = settings)
    );
  }

  get settings(): Setting {
    return this._settings;
  }

  set settings(value: Setting) {
    this._settings = value;
  }
}

