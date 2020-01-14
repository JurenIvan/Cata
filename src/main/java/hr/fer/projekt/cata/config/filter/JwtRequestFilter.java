package hr.fer.projekt.cata.config.filter;

import hr.fer.projekt.cata.config.errorHandling.CATAException;
import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.config.security.util.JwtUtil;
import hr.fer.projekt.cata.domain.ApiKey;
import hr.fer.projekt.cata.repository.ApikeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.List.*;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	private UserDetailsServiceImpl userDetailsService;
	private ApikeyRepository apikeyRepository;
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null) {
			chain.doFilter(request, response);
			return;
		}

		String jwt = authorizationHeader.substring(7);
		String username = jwtUtil.extractUsername(jwt);

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = null;

			if (authorizationHeader.startsWith("Apikey ")) {
				ApiKey a = apikeyRepository.findByApikeyUserName(username).orElseThrow(CATAException::new);
				userDetails = new User(a.getApikeyUserName(), a.getApikeyHash(), of(a.getRole().getGrantedAuthority()));
			} else if (authorizationHeader.startsWith("Bearer ")) {
				userDetails = this.userDetailsService.loadUserByUsername(username);
			} else
				throw new CATAException();

			if (jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}