package com.soa.tripmanager.config;

import com.soa.tripmanager.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/service/public/**").permitAll() // Allow public endpoints
                .antMatchers("/service/private/**").authenticated() // Require authentication for private endpoints
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(), BasicAuthenticationFilter.class); // Add JWT authentication filter
    }
}
