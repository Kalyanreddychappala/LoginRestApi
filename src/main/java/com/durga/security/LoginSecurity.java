package com.durga.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class LoginSecurity {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf->csrf.disable()).authorizeHttpRequests(auth->auth.requestMatchers("/login").permitAll()
				.anyRequest().authenticated()
				);
		// ✅ Add JWT filter after permitting login
        http.addFilterBefore(requestFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	@Bean 
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public JwtRequestFilter requestFilter() {
		return new JwtRequestFilter();
	}
}
