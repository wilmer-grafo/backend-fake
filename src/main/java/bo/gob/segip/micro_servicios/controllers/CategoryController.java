package bo.gob.segip.micro_servicios.controllers;

import bo.gob.segip.micro_servicios.payloads.ApiResponse;
import bo.gob.segip.micro_servicios.payloads.CategoryDto;
import bo.gob.segip.micro_servicios.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    //	create Category

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

        CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(createCategoryDto, (HttpStatus.CREATED));

    }

    //	update Category

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto CategoryDto, @PathVariable Integer id) {

        CategoryDto updatedCategory = this.categoryService.updateCategory(CategoryDto, id);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);

    }

    //	delete Category

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {

        this.categoryService.deleteCategory(id);

        return new ResponseEntity<>(new ApiResponse("Deleted successfully", true), HttpStatus.OK);

    }

    //	Get Categories

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories() {

        return ResponseEntity.ok(this.categoryService.getCategories());

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id) {

        return ResponseEntity.ok(this.categoryService.getCategoryById(id));

    }

}
