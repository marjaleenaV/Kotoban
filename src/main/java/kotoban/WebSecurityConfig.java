package kotoban;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

	private UserDetailsService userDetailsService;

	// Constructor injection
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {

		http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/login", "/signup", "/saveuser", "/css/**", "/error").permitAll() // Enable css when logged out
						.anyRequest().authenticated())
				.formLogin(formlogin -> formlogin
						.loginPage("/login")
						.defaultSuccessUrl("/", true)
						.permitAll()// Login page
				).logout(logout -> logout
						.permitAll());
		return http.build();
	}
}