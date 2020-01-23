package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.domain.User;
import hr.fer.projekt.cata.domain.enums.Role;
import hr.fer.projekt.cata.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isUserNameAvailable(String username) {
        return userRepository.findAllByUsername(username).isEmpty();
    }

    public boolean isEmailAvailable(String email) {
        return userRepository.findAllByEmail(email).isEmpty();
    }

    public void saveUser(String email, String password, String username) throws Exception {
        if (!isEmailAvailable(email))
            throw new Exception("email not available");

        if (!isUserNameAvailable(username))
            throw new Exception("username not available");

        var user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt(12)));
        user.setRoles(List.of(Role.VISITOR));

        userRepository.save(user);
    }
}