package bo.gob.segip.micro_servicios.controllers;

import bo.gob.segip.micro_servicios.payloads.ApiResponse;
import bo.gob.segip.micro_servicios.payloads.UserDto;
import bo.gob.segip.micro_servicios.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    //	create USER
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        UserDto createUserDto = this.userService.createUser(userDto);

        return new ResponseEntity<>(createUserDto, (HttpStatus.CREATED));

    }


    //	update USER
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @Valid @RequestBody UserDto userDto,
            @PathVariable Integer id) {

        UserDto updatedUser = this.userService.updateUser(userDto, id);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    //	delete User

    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {

        this.userService.deleteUser(id);

        return ResponseEntity.ok(Map.of("Message", "User Deleted successfully"));

    }
    */

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {

        this.userService.deleteUser(id);

        return new ResponseEntity<>(
                new ApiResponse("Deleted successfully", true), HttpStatus.OK);

    }

    //	Get users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(this.userService.getAllUsers());

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {

        return ResponseEntity.ok(this.userService.getUserById(id));

    }

}
