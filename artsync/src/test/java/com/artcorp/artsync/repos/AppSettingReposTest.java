package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.AppSetting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class AppSettingReposTest {
    @Autowired
    private AppSettingRepos appSettingRepos;

    @Test
    public void initialiserAppSetting(){
        List<AppSetting> appSettings = appSettingRepos.findAll();
        if (appSettings.isEmpty()){
            AppSetting appSetting = new AppSetting();
            appSetting.setBoiteIdeeActive(true);
            appSetting.setForgeImageActive(true);
            appSettingRepos.save(appSetting);
        }
    }

    @Test
    public void findFirst(){
        AppSetting appSetting = appSettingRepos.findFirstByOrderById();
        System.out.println(appSetting);
    }
}