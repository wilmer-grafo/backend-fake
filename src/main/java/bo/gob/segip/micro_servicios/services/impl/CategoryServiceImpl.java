package bo.gob.segip.micro_servicios.services.impl;

import bo.gob.segip.micro_servicios.entities.CategoryEntity;
import bo.gob.segip.micro_servicios.exceptions.ResourceNotFoundException;
import bo.gob.segip.micro_servicios.payloads.CategoryDto;
import bo.gob.segip.micro_servicios.repositories.ICategoryRepository;
import bo.gob.segip.micro_servicios.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        CategoryEntity category = this.modelMapper.map(categoryDto, CategoryEntity.class);
        CategoryEntity addedCat = this.categoryRepository.save(category);

        return this.modelMapper.map(addedCat, CategoryDto.class);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {

        CategoryEntity category = this.categoryRepository.findById(catId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category Id", catId));

        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        CategoryEntity updatedCat = this.categoryRepository.save(category);

        return this.modelMapper.map(updatedCat, CategoryDto.class);

    }

    @Override
    public void deleteCategory(Integer catId) {

        CategoryEntity cat = this.categoryRepository.findById(catId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category Id", catId));

        this.categoryRepository.delete(cat);

    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {

        CategoryEntity cat = this.categoryRepository.findById(catId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category Id", catId));

        return this.modelMapper.map(cat, CategoryDto.class);

    }

    @Override
    public List<CategoryDto> getCategories() {

        List<CategoryEntity> categories = this.categoryRepository.findAll();
        List<CategoryDto> list
                = categories.stream()
                .map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());

        return list;

    }

}
