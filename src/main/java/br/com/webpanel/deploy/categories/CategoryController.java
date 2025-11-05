package br.com.webpanel.deploy.categories;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.webpanel.deploy.categories.dto.CreateCategoryDto;
import br.com.webpanel.deploy.categories.dto.RecoveryCategoryDto;

@RestController
@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Categories", description = "Category management endpoints")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Category created"),
        @ApiResponse(responseCode = "400", description = "Invalid input - name is empty or too long"),
        @ApiResponse(responseCode = "409", description = "Category name already exists")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecoveryCategoryDto> create(@Valid @RequestBody CreateCategoryDto dto) {
        RecoveryCategoryDto created = service.createCategory(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "List all categories")
    @ApiResponse(responseCode = "200", description = "Categories found")
    @GetMapping
    public ResponseEntity<List<RecoveryCategoryDto>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get a category by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category found"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecoveryCategoryDto> getById(
            @Parameter(description = "Category ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Update a category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "409", description = "Category name already exists")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecoveryCategoryDto> update(
            @Parameter(description = "Category ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CreateCategoryDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Category deleted"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Category ID", example = "1")
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
