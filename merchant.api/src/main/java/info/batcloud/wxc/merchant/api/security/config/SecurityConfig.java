package info.batcloud.wxc.merchant.api.security.config;

import info.batcloud.wxc.core.service.StoreUserService;
import info.batcloud.wxc.merchant.api.security.filter.JwtAuthorizationTokenFilter;
import info.batcloud.wxc.merchant.api.security.filter.PasswordAuthenticationJsonFilter;
import info.batcloud.wxc.merchant.api.security.filter.PhoneLoginAuthenticationProcessingFilter;
import info.batcloud.wxc.merchant.api.security.handler.JsonLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private PasswordAuthenticationJsonFilter passwordAuthenticationJsonFilter;

    @Inject
    private JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter;

    @Inject
    private PhoneLoginAuthenticationProcessingFilter phoneLoginAuthenticationProcessingFilter;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private JsonLogoutHandler logoutHandler;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(storeUserService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        passwordAuthenticationJsonFilter.setRememberMeServices(rememberMeService());
        passwordAuthenticationJsonFilter.setAuthenticationManager(this.authenticationManager());
//        phoneLoginAuthenticationProcessingFilter.setRememberMeServices(rememberMeService());
        phoneLoginAuthenticationProcessingFilter.setAuthenticationManager(this.authenticationManager());
        http.cors();
//        http.addFilterBefore(rememberMeAuthenticationFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(phoneLoginAuthenticationProcessingFilter, BasicAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthorizationTokenFilter, BasicAuthenticationFilter.class);
        http.addFilter(passwordAuthenticationJsonFilter);
        http.logout().invalidateHttpSession(true).addLogoutHandler(logoutHandler);
        http.rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(Integer.MAX_VALUE)
                .userDetailsService(storeUserService);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
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
