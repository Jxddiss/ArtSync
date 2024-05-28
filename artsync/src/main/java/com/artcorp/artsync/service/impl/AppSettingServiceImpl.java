package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.AppSetting;
import com.artcorp.artsync.repos.AppSettingRepos;
import com.artcorp.artsync.service.AppSettingService;
import org.springframework.stereotype.Service;

@Service
public class AppSettingServiceImpl implements AppSettingService {
    private final AppSettingRepos appSettingRepos;

    public AppSettingServiceImpl(AppSettingRepos appSettingRepos) {
        this.appSettingRepos = appSettingRepos;
    }

    @Override
    public AppSetting getAppSetting() {
        return appSettingRepos.findFirstByOrderById();
    }

    @Override
    public AppSetting updateSettings(AppSetting appSetting) {
        AppSetting appSettingSaved = appSettingRepos.findFirstByOrderById();
        if (appSettingSaved == null){
            return appSettingRepos.save(appSetting);
        }
        appSettingSaved.setForgeImageActive(appSetting.isForgeImageActive());
        appSettingSaved.setBoiteIdeeActive(appSetting.isBoiteIdeeActive());
        return appSettingRepos.save(appSettingSaved);
    }
}
