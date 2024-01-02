package bo.gob.segip.micro_servicios.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "blog", name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @Column(name = "titulo_categoria", length = 100, nullable = false)
    private String categoryTitle;

    @Column(name = "descripcion_categoria")
    private String categoryDescription;

    // Relations

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) // category en PostEntity
    private List<PostEntity> posts = new ArrayList<>();

}
