package com.zdjavapol110.eventmanager.core.modules.user.webconfig;

import com.zdjavapol110.eventmanager.core.modules.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class WebConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                    .authorizeRequests()
                        .antMatchers("/users").authenticated()
                        .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .usernameParameter("email")
                    .defaultSuccessUrl("/users")
                    .permitAll()
                .and()
                    .logout().logoutSuccessUrl("/").permitAll()
                .and()
//                    .authorizeHttpRequests((authz) -> authz
//                        .anyRequest().authenticated()
//                )
                .httpBasic(withDefaults())
                .authenticationProvider(authenticationProvider()).build();
    }

//    @Override
//    protected void configure(HttpSecurity httpsds) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/users").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .usernameParameter("email")
//                .defaultSuccessUrl("/users")
//                .permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/").permitAll();
//    }

}
