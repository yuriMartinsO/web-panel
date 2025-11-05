package br.com.webpanel.deploy.categories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.webpanel.deploy.categories.dto.CreateCategoryDto;
import br.com.webpanel.deploy.categories.dto.RecoveryCategoryDto;

/**
 * Service layer for Category CRUD operations.
 */
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public RecoveryCategoryDto createCategory(CreateCategoryDto dto) {
        // Check if name already exists
        if (repository.existsByNameIgnoreCase(dto.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A category with name '" + dto.name() + "' already exists");
        }

        Category entity = mapper.toEntity(dto);
        Category saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<RecoveryCategoryDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RecoveryCategoryDto getById(Long id) {
        Category c = repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found")
        );
        return mapper.toDto(c);
    }

    public RecoveryCategoryDto update(Long id, CreateCategoryDto dto) {
        Category existing = repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found")
        );
        mapper.updateEntityFromDto(dto, existing);
        Category saved = repository.save(existing);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        repository.deleteById(id);
    }
}
