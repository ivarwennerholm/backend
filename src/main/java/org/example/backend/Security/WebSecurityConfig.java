package org.example.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserServiceImpl();
//    }
    @Autowired
    private UserServiceImpl userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/",  "/bookings/**","/customers/**", "/blacklist/**","/contractcustomers/**",
                                "/rooms/**","/roomevents/**","/roomtypes/**","/shippingcontractors/**",
                                "/js/**", "/css/**", "/images/**", "/login/**", "/logout","/queues/**","/login","/forgotpassword","/passwordreset/**","/login-error","/access-denied").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                   .loginPage("/login")     //use custom login page
                   .loginProcessingUrl("/login")    //custom login page handles login failure instead of default ones
                   .failureForwardUrl("/login-error")
                )
                .logout((logout) -> {
                    logout.permitAll();
                    logout.logoutSuccessUrl("/");
                })
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler())) //user without authority redirect to custom access denied page
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
