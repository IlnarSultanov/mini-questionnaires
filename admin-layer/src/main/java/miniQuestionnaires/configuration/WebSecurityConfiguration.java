package miniQuestionnaires.configuration;


import miniQuestionnaires.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final AuthenticationSuccessHandler authSuccessHandler;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfiguration(UserService userService,
                                    AuthenticationSuccessHandler authSuccessHandler,
                                    BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authSuccessHandler = authSuccessHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/api/admin/**").hasRole("ADMIN")
                    .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/registration/**").permitAll()
                    .and()
                .headers()
                    .frameOptions().disable()
                    .and()
                .formLogin()
                    .loginPage("/api/auth")
                    .failureUrl("/api/authError")
                    .loginProcessingUrl("/authUser")
                    .permitAll()
                    .successHandler(authSuccessHandler)
                    .and()
                .logout()
                    .logoutSuccessUrl("/api/auth")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/api/auth");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}