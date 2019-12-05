package hr.fer.zemris.opp.giger.domain.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    PERSON,
    MUSICIAN,
    ORGANIZER;

    public GrantedAuthority getGrantedAuthority() {
        return new SimpleGrantedAuthority(name());
    }
}