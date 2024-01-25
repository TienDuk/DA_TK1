package com.example.webbanhang.security;

import com.example.webbanhang.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                configure -> configure
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/cart/**").hasRole("USER")
                        .requestMatchers("/home/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/register/**").permitAll()
                        .anyRequest().permitAll()
        ).formLogin(
                form->form.loginPage("/showLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
        ).logout(
                logout-> logout.permitAll()
        ).exceptionHandling(
                configeee->configeee.accessDeniedPage("/p403")
        );

//        http.httpBasic(Customizer.withDefaults());
//        http.csrf(csrf-> csrf.disable());
        return http.build();
    }
}
