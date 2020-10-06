package webBackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям
    private final SessionRegistry sessionRegistry;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                          SuccessUserHandler successUserHandler,
                          SessionRegistry sessionRegistry) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
        this.sessionRegistry = sessionRegistry;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable(); - попробуйте выяснить сами, что это даёт
        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasAuthority('ADMIN')")
                .antMatchers("/user").access("hasAuthority('USER')") // разрешаем входить на /user пользователям с ролью User
                .antMatchers("/").permitAll() // доступность всем
//                .anyRequest().authenticated()
                .and().formLogin()  // Spring сам подставит свою логин форму
//                .loginPage("login.html")
//                .loginProcessingUrl("/login")
                .failureUrl("/login.html?error=true")
                .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправления по ролям

        http
                .sessionManagement()
                .maximumSessions(1).sessionRegistry(sessionRegistry);

        http.logout()
                .logoutSuccessUrl("/");
    }

    // Необходимо для шифрования паролей
    // В данном примере не используется, отключен
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}