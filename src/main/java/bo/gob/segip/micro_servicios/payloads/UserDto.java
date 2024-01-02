package bo.gob.segip.micro_servicios.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 4, message = "Username must have atleast 4 characters")
    private String name;

    @Email(message = "Invalid Email")
    private String email;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 3, max = 10, message = "Password must be at least of 3 char and maximum of 10")
    private String password;

    @NotEmpty(message = "Can not be empty")
    private String about;

}
