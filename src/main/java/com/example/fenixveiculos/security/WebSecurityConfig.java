package com.example.fenixveiculos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.fenixveiculos.service.AuthenticationService;
import com.example.fenixveiculos.utils.EncoderUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenAuthenticationFilter jwtFilter;
	private final AuthenticationService authService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// disable CSRF
				.csrf().disable()
				// session management to stateless
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				// ** endpoint permissions
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/api/v1/auth/currentUser").authenticated()
				// * public endpoints
				.antMatchers("/api/v1/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/cars/**").permitAll()
				// * restrict endpoints
				.antMatchers("/api/v1/**").authenticated()
				// filters
				.and().addFilterBefore(jwtFilter,
						UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(authService)
				.passwordEncoder(EncoderUtils.getInstance());
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
