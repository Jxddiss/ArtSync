package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.entity.AppSetting;
import com.artcorp.artsync.service.AppSettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppSettingAdminRestController {
    private final AppSettingService appSettingService;

    public AppSettingAdminRestController(AppSettingService appSettingService) {
        this.appSettingService = appSettingService;
    }

    @GetMapping("/api/app-setting")
    public ResponseEntity<AppSetting> getAppSetting(){
        return new ResponseEntity<>(appSettingService.getAppSetting(), HttpStatus.OK);
    }

    @PutMapping("/api/app-setting")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public ResponseEntity<AppSetting> updateAppSetting(@RequestBody AppSetting appSetting){
        return new ResponseEntity<>(appSettingService.updateSettings(appSetting),HttpStatus.OK);
    }
}
