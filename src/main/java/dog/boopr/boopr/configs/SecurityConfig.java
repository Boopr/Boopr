package dog.boopr.boopr.configs;

import dog.boopr.boopr.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServices userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll();

    }

    //create the authorities mapper so we can have defaults on how permissions are handled without information 
    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper(){
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true);
        authorityMapper.setDefaultAuthority("USER");
        return authorityMapper;
    }
    //We then take the preferences created in authroitiesMapper
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(11));
        provider.setAuthoritiesMapper(authoritiesMapper());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //we are getting authetnicationmanagerBuilder and passing it the bean we just created
        auth.authenticationProvider(authenticationProvider());
    }

}
