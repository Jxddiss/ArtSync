package com.artcorp.artsync.configuration;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HibernateCustomizer implements HibernatePropertiesCustomizer {
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.query.plan_cache_max_size", 40);
        hibernateProperties.put("hibernate.query.plan_parameter_metadata_max_size", 40);
    }
}
