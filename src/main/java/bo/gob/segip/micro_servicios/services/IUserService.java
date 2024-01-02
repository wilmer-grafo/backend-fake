package bo.gob.segip.micro_servicios.services;

import bo.gob.segip.micro_servicios.payloads.UserDto;

import java.util.List;

public interface IUserService {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser (Integer userId);

}
