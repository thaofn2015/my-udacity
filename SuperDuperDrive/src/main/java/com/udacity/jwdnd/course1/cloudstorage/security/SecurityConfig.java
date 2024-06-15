package com.udacity.jwdnd.course1.cloudstorage.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationProviderImpl authenticationProviderImpl;

	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		// Page not need login
		http.authorizeRequests().antMatchers("/css/**", "/js/**",
											 "/signup", "/login", "/logout").permitAll();

		http.authorizeRequests().anyRequest().authenticated();

		// Config login form
		http.authorizeRequests()
					.and().formLogin()
							.loginPage("/login")
							.defaultSuccessUrl("/home", true)
					.and().logout()
							.logoutUrl("/logout")
							.invalidateHttpSession(true).deleteCookies("JSESSIONID")
							.logoutSuccessUrl("/login?logout");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProviderImpl);
	}
}
