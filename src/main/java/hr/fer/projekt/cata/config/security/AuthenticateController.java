package hr.fer.projekt.cata.config.security;

import hr.fer.projekt.cata.config.security.model.AuthenticationRequestDto;
import hr.fer.projekt.cata.config.security.model.AuthenticationResponseDto;
import hr.fer.projekt.cata.config.security.util.JwtUtil;
import hr.fer.projekt.cata.domain.enums.Role;
import hr.fer.projekt.cata.web.rest.controller.ValidationHandlingController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticateController extends ValidationHandlingController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthenticationRequestDto authenticationRequest, BindingResult bindingResult) throws Exception {
        handleValidation(bindingResult);
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponseDto(jwt));
    }

    @GetMapping(value = "/is-agent")
    public boolean isLoggedInUserAgent() {
        return userDetailsService.getLoggedUser().getRoles().contains(Role.ORGANIZER);
    }
}
