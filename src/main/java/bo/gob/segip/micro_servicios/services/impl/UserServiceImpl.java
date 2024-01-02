package bo.gob.segip.micro_servicios.services.impl;

import bo.gob.segip.micro_servicios.entities.UserEntity;
import bo.gob.segip.micro_servicios.exceptions.ResourceNotFoundException;
import bo.gob.segip.micro_servicios.payloads.UserDto;
import bo.gob.segip.micro_servicios.repositories.IUserRepository;
import bo.gob.segip.micro_servicios.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto pUser) {
        UserEntity user = this.dtoToUser(pUser);
        UserEntity savedUser = this.userRepository.save(user);

        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto pUser, Integer userId) {

        UserEntity user = this.userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException("User", " id ", userId));

        user.setAbout(pUser.getAbout());
        user.setEmail(pUser.getEmail());
        user.setName(pUser.getName());
        user.setPassword(pUser.getPassword());

        UserEntity updatedUser = this.userRepository.save(user);
        UserDto userToDto = this.userToDto(updatedUser);

        return userToDto;

    }

    @Override
    public UserDto getUserById(Integer userId) {

        UserEntity user = this.userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException("User", " id ", userId));

        return this.userToDto(user);

    }

    @Override
    public List<UserDto> getAllUsers() {

        List<UserEntity> list = this.userRepository.findAll();
        List<UserDto> userDto = list.stream().map(l ->
                this.userToDto(l)).collect(Collectors.toList());

        return userDto;

    }

    @Override
    public void deleteUser(Integer userId) {

        UserEntity user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", " id ", userId));

        this.userRepository.delete(user);

    }

    // Using Model Mapper

    public UserEntity dtoToUser(UserDto userDto) {

        UserEntity user = this.modelMapper.map(userDto, UserEntity.class);

        return user;

    }

    public UserDto userToDto(UserEntity user) {

        UserDto userDto = this.modelMapper.map(user, UserDto.class);

        return userDto;

    }

    // Conventional object

    /*
    public UserEntity dtoToUser(UserDto userDto) {

        UserEntity user = new UserEntity();
        user.setId(userDto.getId());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());

        return user;

    }

    public UserDto userToDto(UserEntity user) {

        UserDto userDto = new UserDto();
        userDto.setAbout(user.getAbout());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());

        return userDto;

    }
    */

}
