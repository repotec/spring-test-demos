package com.integration.container.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.integration.container.test.filter.JwtGeneratorFilter;
import com.integration.container.test.filter.JwtValidatorFilter;


@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
		  http
          .cors().disable()
          .csrf().disable()
  		  .addFilterBefore(new JwtValidatorFilter(), BasicAuthenticationFilter.class)
  		  .addFilterAfter(new JwtGeneratorFilter(), BasicAuthenticationFilter.class)
          .authorizeHttpRequests((auth) ->{
        	  	//public end-points
			    auth.antMatchers(HttpMethod.POST, "/users").permitAll();
			    auth.antMatchers(HttpMethod.POST, "/login").permitAll();
				auth.antMatchers("/h2-console/**").permitAll(); //required for h2-console
				
				//private end-points
				auth.anyRequest().authenticated();
				
			}).httpBasic(Customizer.withDefaults())
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		  .and()
		  .headers().frameOptions().disable(); //required for h2-console
		  
		  return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}