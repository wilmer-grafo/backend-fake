package bo.gob.segip.micro_servicios.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "e_departments")
@Table(schema = "otros", name = "departments")
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartamentEntity {

    @Id
    @GeneratedValue
    @Column(name = "department_id")
    private Integer id;

    @Column(name = "department_name")
    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CourseEntity> course = new ArrayList<>();

}
