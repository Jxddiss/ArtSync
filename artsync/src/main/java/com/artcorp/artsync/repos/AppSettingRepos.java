package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSettingRepos extends JpaRepository<AppSetting, Long> {
    AppSetting findFirstByOrderById();
}
