package net.proselyte.springbootdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests().antMatchers(HttpMethod.GET, "/note/", "/note/notes", "/user/", "/user/users")
                .hasAuthority("SCOPE_read")
                .antMatchers(HttpMethod.POST, "/createNote","/createUser" )
                .hasAuthority("SCOPE_write").anyRequest().authenticated().and().oauth2ResourceServer().jwt();
    }
}
