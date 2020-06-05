package com.cio.gidservice.gidservice.entities.databaseEntities;

import com.cio.gidservice.gidservice.entities.databaseEntities.Organization;
import com.cio.gidservice.gidservice.entities.requestEntities.UserRequestEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Data
@Entity
@Table(name="usr")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String phoneNumber;
    private String login;
    private String password;
    private String name;

    //Связь с заведениями, которые принадлежат пользователю
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Organization> organizationList;

    public User(UserRequestEntity entity) {
        this.phoneNumber = entity.getPhoneNumber();
        this.login = entity.getLogin();
        this.name = entity.getName();
        this.password = entity.getPassword();
        this.organizationList = entity.getOrganizationList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login) &&
                password.equals(user.password) &&
                name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name);
    }

    public void addOrganization(Organization organization) {
        organizationList.add(organization);
    }
}

