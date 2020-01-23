package hr.fer.zemris.opp.giger.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static java.util.List.of;

@Configuration
public class CorsConfig {

	@Bean
	public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(of("http://localhost:3000", "http://localhost:8080", "https://giger-fer.herokuapp.com", "https://giger-fer-dev.herokuapp.com", "https://giger-fer.herokuapp.com/", "https://giger-fer-dev.herokuapp.com/", 
		"http://giger-fer-dev.herokuapp.com", "http://giger-fer-dev.herokuapp.com/"));
		config.setAllowedMethods(of("*"));
		config.setAllowedHeaders(of("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
