package bo.gob.segip.micro_servicios.repositories;

import bo.gob.segip.micro_servicios.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

//    Optional<UserEntity> findByEmail(String email);

//    UserEntity findByEmail(String email);

}