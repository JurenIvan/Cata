package hr.fer.projekt.cata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
public class Users {

    @Id
    private long id;
    private String name;
    private int age;

}
