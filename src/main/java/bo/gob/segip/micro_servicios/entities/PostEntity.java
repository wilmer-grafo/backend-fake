package bo.gob.segip.micro_servicios.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "blog", name = "publicaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo_publicacion", length = 100, nullable = false)
    private String title;

    @Column(name = "contenido_publicacion", length = 100, nullable = false)
    private String content;

    @Column(name = "imagen_publicacion")
    private String imageName;

    @Column(name = "fecha_publicacion")
    private Date addedDate;

    // Relations

    @ManyToOne
    @JoinColumn(name = "categoria_id") // nueva columna
    private CategoryEntity category; // mappedBy = "category" en CategoryEntity

    @ManyToOne
    @JoinColumn(name = "usuario_id") // nueva columna
    private UserEntity user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL) // post en CommentEntity
    private Set<CommentEntity> comments = new HashSet<>();

//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name="users_role", joinColumns =@JoinColumn(name="users", referencedColumnName = "id"),
//				inverseJoinColumns = @JoinColumn(name="role", referencedColumnName = "id"))
//	private Set<Role> roles = new HashSet<>();

}
