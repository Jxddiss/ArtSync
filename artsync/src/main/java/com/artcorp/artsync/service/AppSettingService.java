package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.AppSetting;

public interface AppSettingService {
    AppSetting getAppSetting();
    AppSetting updateSettings(AppSetting appSetting);
}
