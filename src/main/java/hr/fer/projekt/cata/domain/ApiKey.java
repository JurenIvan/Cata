package hr.fer.projekt.cata.domain;

import hr.fer.projekt.cata.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiKey {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String apikeyUserName;
	private String apikeyHash;
	private Role role;
}
