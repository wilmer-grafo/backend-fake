package bo.gob.segip.micro_servicios.services;

import bo.gob.segip.micro_servicios.payloads.CategoryDto;

import java.util.List;

public interface ICategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);

    void deleteCategory(Integer catId);

    CategoryDto getCategoryById(Integer catId);

    List<CategoryDto> getCategories();

}
