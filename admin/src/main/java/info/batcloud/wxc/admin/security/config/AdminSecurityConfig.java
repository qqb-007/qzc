package info.batcloud.wxc.admin.security.config;

import info.batcloud.wxc.admin.security.filter.UsernamePasswordAuthenticationJsonFilter;
import info.batcloud.wxc.admin.service.ManagerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private ManagerService managerService;

    @Inject
    private DataSource dataSource;

    @Inject
    private UsernamePasswordAuthenticationJsonFilter usernamePasswordAuthenticationJsonFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(managerService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // Configure spring security's authenticationManager with custom
        // user details service
        auth.userDetailsService(managerService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(usernamePasswordAuthenticationJsonFilter)
                .authorizeRequests()
                .antMatchers("/", "/api/login/check").permitAll()
                .anyRequest().hasAuthority("ROLE_ADMIN")
                .and()
                //通过formLogin()定义当需要用户登录时候，转到的登录页面。
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                //注销
                .logout()
                .permitAll();
        //关闭csrf 防止循环定向
        http.csrf().disable();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        persistentTokenRepository.setDataSource(dataSource);
        return persistentTokenRepository;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().regexMatchers("^/(css|images|js)/.+$").regexMatchers("^.+\\.html(\\?.*)??$");
    }
}
