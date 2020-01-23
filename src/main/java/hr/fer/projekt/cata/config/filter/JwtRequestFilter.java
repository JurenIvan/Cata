package hr.fer.projekt.cata.config.filter;

import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.config.security.util.JwtUtil;
import hr.fer.projekt.cata.domain.ApiKey;
import hr.fer.projekt.cata.domain.exception.CataException;
import hr.fer.projekt.cata.repository.ApikeyRepository;
import lombok.RequiredArgsConstructor;
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

import static hr.fer.projekt.cata.domain.exception.ErrorCode.EXCEPTION;
import static hr.fer.projekt.cata.domain.exception.ErrorCode.NO_SUCH_APIKEY;
import static java.util.List.of;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String APIKEY = "Apikey ";
    public static final String BEARER = "Bearer ";

    private final UserDetailsServiceImpl userDetailsService;
    private final ApikeyRepository apikeyRepository;
    private final JwtUtil jwtUtil;

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
            UserDetails userDetails;

            if (authorizationHeader.startsWith(APIKEY)) {
                ApiKey a = apikeyRepository.findByApikeyUserName(username).orElseThrow(() -> new CataException(NO_SUCH_APIKEY));
                userDetails = new User(a.getApikeyUserName(), a.getApikeyHash(), of(a.getRole().getGrantedAuthority()));
            } else if (authorizationHeader.startsWith(BEARER))
                userDetails = this.userDetailsService.loadUserByUsername(username);
            else
                throw new CataException(EXCEPTION);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}