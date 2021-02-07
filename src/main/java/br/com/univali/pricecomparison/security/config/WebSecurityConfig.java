package br.com.univali.pricecomparison.security.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.univali.pricecomparison.security.service.TokenAuthenticationService;


@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
public abstract class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	protected void configAuthentication(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder,
			@Autowired UserDetailsService userDetailsService)
			throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(usersByUsernameQuery())
				.authoritiesByUsernameQuery(authoritiesByUsernameQuery())
				.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		httpRoutesConfig(http);
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(BCryptVersion.$2A);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(),HttpMethod.PUT.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name() ));
		configuration.setAllowedHeaders(Arrays.asList(TokenAuthenticationService.HEADER_STRING, "Cache-Control", "Content-Type"));
		configuration.addExposedHeader(TokenAuthenticationService.HEADER_STRING);
		configuration.setAllowCredentials(true);
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
	
	protected abstract String usersByUsernameQuery();
	protected abstract String authoritiesByUsernameQuery();
	protected abstract void httpRoutesConfig(HttpSecurity http) throws Exception;

}
