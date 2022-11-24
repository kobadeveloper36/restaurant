package com.progect.ui.config;

import com.progect.ui.security.UserDetailsServiceImpl;
import com.progect.ui.security.UsersRoles;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority(UsersRoles.ADMIN.getAuthority())
                .antMatchers("/login", "/registration", "/css/**",
                        "/images/**", "/img/**", "/", "/menu", "/aboutUs", "/cart/**", "/addComment/**").permitAll()
                .anyRequest()
                .hasAnyAuthority(UsersRoles.USER.getAuthority(), UsersRoles.ADMIN.getAuthority())
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/process_login")
                .failureUrl("/login&error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }
}
