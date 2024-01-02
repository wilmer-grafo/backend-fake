package bo.gob.segip.micro_servicios.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private int id;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 4, message = "Content must have atleast 4 characters")
    private String content;

    @NotEmpty(message = "Can not be empty")
    private String title;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();

}

