package webBackend.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String lastName;

    @NotNull
    @Positive
    @Column
    private long age;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Role> roles = new ArrayList<>();

    public User() {}

    public User(String name,    String lastName, long age, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add( role );
        role.getUsers().add( this );
    }

    public void removeRole(Role role) {
        roles.remove( role );
        role.getUsers().remove( this );
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        return b.append("The user id is: ")
                .append(this.id)
                .append("\n the name is: ")
                .append(this.name)
                .append("\n the email is: ")
                .append(this.email)
                .append("\n the password is: ")
                .append(this.password).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getAge() == user.getAge() &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getLastName(), getAge(), getEmail());
    }
}
