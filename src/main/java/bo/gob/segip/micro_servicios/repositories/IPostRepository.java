package bo.gob.segip.micro_servicios.repositories;

import bo.gob.segip.micro_servicios.entities.CategoryEntity;
import bo.gob.segip.micro_servicios.entities.PostEntity;
import bo.gob.segip.micro_servicios.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPostRepository extends JpaRepository<PostEntity, Integer> {

    List<PostEntity> findByUser(UserEntity user);

    List<PostEntity> findByCategory(CategoryEntity category);

    List<PostEntity> findByTitleContaining(String title);

    @Query("SELECT p FROM PostEntity p WHERE p.title LIKE :key")
    List<PostEntity> searchByTitle(@Param("key") String title);

}