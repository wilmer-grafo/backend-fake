package bo.gob.segip.micro_servicios.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "e_students")
@Table(schema = "otros", name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer id;

    @Column(name = "student_name")
    private String name;

    @Column(name = "student_full_time")
    private String fullTime;

    @Column(name = "student_age")
    private String age;

    // CREARA NUEVA TABLA
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(schema = "otros", name = "enrolments",
            joinColumns = {@JoinColumn(name = "fk_student_id")},
            inverseJoinColumns = {@JoinColumn(name = "fk_course_id")})
    private List<CourseEntity> course = new ArrayList<>();

}
