package bo.gob.segip.micro_servicios.repositories;

import bo.gob.segip.micro_servicios.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository<CommentEntity, Integer> {
}
