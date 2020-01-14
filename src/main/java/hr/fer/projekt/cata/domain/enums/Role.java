package hr.fer.projekt.cata.domain.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    VISITOR,
    ORGANIZER,
	APIKEY_CAMUNDA;

    public GrantedAuthority getGrantedAuthority() {
        return new SimpleGrantedAuthority(name());
    }
}