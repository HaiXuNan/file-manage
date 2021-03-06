package com.file.manage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        // v1是接口访问前缀,注意与自己的项目区别
        web.ignoring().antMatchers("/**");
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/toLogin", "/css/**", "/fonts/**", "/images/**", "/js/**", "/lib/**").permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/toLogin").failureUrl("/error")
                .and()
                .exceptionHandling().accessDeniedPage("/error");
        http.logout().logoutSuccessUrl("/");
    }*/
}
