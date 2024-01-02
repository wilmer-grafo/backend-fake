package bo.gob.segip.micro_servicios.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

    private Integer categoryId;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 5, message = "Title must have at least 5 characters")
    private String categoryTitle;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 5, message = "Description must have at least 5 characters")
    private String categoryDescription;

}
