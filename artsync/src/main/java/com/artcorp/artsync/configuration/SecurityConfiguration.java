package com.artcorp.artsync.configuration;

import com.artcorp.artsync.exception.domain.CustomAccessDeniedHandler;
import com.artcorp.artsync.filter.CustomAuthenticationEntryPoint;
import com.artcorp.artsync.filter.JwtAuthorizationFilter;
import com.artcorp.artsync.handlers.CustomLogoutHandler;
import com.artcorp.artsync.service.impl.UtilisateurServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.artcorp.artsync.constant.SecurityConstant.PUBLIC_URLS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private JwtAuthorizationFilter jwtAuthorizationFilter;
    private CustomLogoutHandler customLogoutHandler;

    @Autowired
    public SecurityConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter,
                                 UtilisateurServiceImpl utilisateurService, CustomLogoutHandler customLogoutHandler) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.customLogoutHandler = customLogoutHandler;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(PUBLIC_URLS).permitAll()
                                .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/deconnexion")
                        .logoutSuccessUrl("/authentification")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .deleteCookies("jwt")
                        .addLogoutHandler(customLogoutHandler)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(new CustomAccessDeniedHandler())
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
