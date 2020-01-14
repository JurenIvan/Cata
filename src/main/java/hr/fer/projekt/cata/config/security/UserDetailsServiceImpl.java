package hr.fer.projekt.cata.config.security;

import hr.fer.projekt.cata.config.errorHandling.CATAException;
import hr.fer.projekt.cata.domain.enums.Role;
import hr.fer.projekt.cata.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findAllByUsername(username).orElseThrow(() -> new UsernameNotFoundException("sad"));
        return new User(user.getUsername(), user.getPasswordHash(), user.getRoles().stream().map(Role::getGrantedAuthority).collect(toList()));
    }

    public hr.fer.projekt.cata.domain.User getLoggedUser() {
        return userRepository.findAllByUsername(getLoggedInUserUsername()).orElseThrow(CATAException::new);
    }

    public String getLoggedInUserUsername() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
