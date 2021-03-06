package com.example.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private UserPrincipalDetailsService userPrincipalDetailsService; 
	
	public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService) {
		this.userPrincipalDetailsService = userPrincipalDetailsService ; 
	}
	
	//data source 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	//authorization //
	@Override
	protected void configure(HttpSecurity http)throws Exception {
		http
		.requiresChannel()
        .anyRequest()
        .requiresSecure(); 
		
		
		
		http 
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/index").authenticated()
		.antMatchers("/Loan/**").hasAnyAuthority("LOANS","SUPER","ADMIN")
		.antMatchers("/Loans/**").hasAnyAuthority("LOANS","SUPER","ADMIN")
		.antMatchers("/Vouchers/**").hasAnyAuthority("LOANS","SUPER","ADMIN")
		.antMatchers("/adminstration/logs/**").hasAnyAuthority("SUPER","ADMIN")
		.antMatchers("/Banks/view/stats/**").hasAnyAuthority("ANALYTICS","SUPER")
		.antMatchers("/charts/**").hasAnyAuthority("ANALYTICS","SUPER","ALLANALYTICS")
		.antMatchers("/dashBoards/**").hasAnyAuthority("ALLANALYTICS","SUPER")
		.antMatchers("/config/**").hasAnyAuthority("SUPER")
		.and()
		.exceptionHandling().accessDeniedPage("/forbidden")
		.and()
		.formLogin().defaultSuccessUrl("/index")
		.loginPage("/login").permitAll()
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").deleteCookies("JSESSIONID");
	}
		
	
	//cause we are using the data base 
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider() ;
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
		return daoAuthenticationProvider ; 
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	

}
