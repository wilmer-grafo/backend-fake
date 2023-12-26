package bo.gob.segip.micro_servicios.dao.repositories;

import bo.gob.segip.micro_servicios.dao.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findByEdadLessThan(int edad);

    List<UserEntity> findByEdadGreaterThanEqual(int edad);

    List<UserEntity> findByNameLike(String name);

    List<UserEntity> findByNameContaining(String name);

    @Query(value = "select * from ms_users where name = ?1 and edad >= ?2 and edad <= ?3", nativeQuery = true)
    List<UserEntity> findAllUsersBetweenAgeAndName(String name, int ageBegin, int ageEnd);

}
