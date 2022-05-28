package com.zdjavapol110.eventmanager.core.modules.user.webconfig;

import com.zdjavapol110.eventmanager.core.modules.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;

import java.security.AuthProvider;
import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class WebConfig {

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }

//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return new ProviderManager(Arrays.asList((AuthenticationProvider) new AuthProvider()));
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().
//        antMatchers("/registration**", "/js/**",
//                        "/css/**", "/img/**").permitAll().anyRequest()
//                .authenticated().and().formLogin().loginPage("/login").
//                permitAll().and().logout()
//                .invalidateHttpSession(true).clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
//                logoutSuccessUrl("/login?logout")
//                .permitAll();
//
//    }

//    @Autowired
//    CustomAuthenticationManager customAuthenticationManager= new CustomAuthenticationManager();
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers( "login").authenticated()
                        .anyRequest()
                        .permitAll()
                .and()
                    .formLogin().loginPage("/login")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/events")
                    .permitAll()
                .and()
                    .logout().logoutSuccessUrl("/").permitAll()
                .and()
                    .httpBasic(withDefaults());
//                    .authenticationProvider(authenticationProvider());
//                .authenticationManager(customAuthenticationManager);
        return http.build();
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
