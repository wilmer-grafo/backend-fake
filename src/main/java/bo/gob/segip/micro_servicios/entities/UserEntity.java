package bo.gob.segip.micro_servicios.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
//@NamedQuery(name = "UserEntity.findByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email")
@Table(schema = "blog", name = "usuarios")
@NoArgsConstructor
@Getter
@Setter
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre_usuario", nullable = false, length = 100)
    private String name;

    @Column(name = "correo_usuario", nullable = false)
    private String email;

    @Column(name = "clave_usuario", nullable = false)
    private String password;

    @Column(name = "info_usuario", nullable = false)
    private String about;

    // Relations

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostEntity> posts = new ArrayList<>();

    // CREARA NUEVA TABLA cuyo nombre sera user_role
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(schema = "blog", name = "user_role",
            joinColumns = @JoinColumn(name = "fk_user", referencedColumnName = "id"), // con columna fk_user y referencia id (PK) de la relacion; en este caso User
            inverseJoinColumns = @JoinColumn(name = "fk_role", referencedColumnName = "rol_id"))
    // con columna fk_rol y referencia rol_id (PK) de la relacion; en este caso Role
    private Set<RoleEntity> roles = new HashSet<>();

    // Metodos de la interfaz UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = this.roles.stream().map((pRole) ->
                new SimpleGrantedAuthority(pRole.getRole())).collect(Collectors.toList());

        return authorities;

    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Mdoficar a true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Mdoficar a true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Mdoficar a true
    }

    @Override
    public boolean isEnabled() {
        return true; // Mdoficar a true
    }

}
