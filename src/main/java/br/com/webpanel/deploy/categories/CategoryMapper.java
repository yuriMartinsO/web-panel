package br.com.webpanel.deploy.categories;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.webpanel.deploy.categories.dto.CreateCategoryDto;
import br.com.webpanel.deploy.categories.dto.RecoveryCategoryDto;

/**
 * Mapper implemented by MapStruct at build time.
 * componentModel = "spring" makes the generated mapper a Spring bean and
 * NullValuePropertyMappingStrategy.IGNORE keeps existing values when updating
 * and the source property is null (useful for partial updates).
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    RecoveryCategoryDto toDto(Category c);

    Category toEntity(CreateCategoryDto dto);

    void updateEntityFromDto(CreateCategoryDto dto, @MappingTarget Category entity);
}
