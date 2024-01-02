package bo.gob.segip.micro_servicios.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
//@NamedQuery(name = "UserEntity.findByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email")
@Table(schema = "blog", name = "usuarios")
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

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

}
