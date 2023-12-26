package bo.gob.segip.micro_servicios.dao.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "e_courses")
@Table(schema = "otros", name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseEntity {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Integer id;

    @Column(name = "course_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_department_id")
    private DepartamentEntity department;

}
