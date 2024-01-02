package bo.gob.segip.micro_servicios.repositories;

import bo.gob.segip.micro_servicios.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}