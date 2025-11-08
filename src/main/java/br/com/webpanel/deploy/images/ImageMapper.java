package br.com.webpanel.deploy.images;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.webpanel.deploy.images.dto.CreateImageDto;
import br.com.webpanel.deploy.images.dto.RecoveryImageDto;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    /**
     * MapStruct mapper instance.
     *
     * Note: when running in a Spring context prefer to inject the mapper bean
     * instead of using this static instance. The field is kept for convenience
     * in tests or static contexts. MapStruct generates the implementation at
     * compile time.
     */
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    /**
     * Convert a {@link CreateImageDto} to an {@link Image} entity.
     *
     * This method performs only a structural mapping between the DTO and the
     * entity. It does not set persistence-related fields (for example id) or
     * perform validation/business logic — those responsibilities belong to the
     * service layer.
     *
     * @param dto the creation DTO containing input data; may be null depending
     *            on MapStruct configuration (caller should follow project
     *            null-handling conventions)
     * @return mapped {@link Image} entity instance (possibly with null id)
     */
    Image createImageDtoToImage(CreateImageDto dto);

    /**
     * Convert an {@link Image} entity to a {@link RecoveryImageDto} used by
     * API responses.
     *
     * The returned DTO contains the fields required by the API contract and
     * should be safe to expose in responses. Mapping is structural only; any
     * presentation-specific adjustments must be done by the caller.
     *
     * @param image the entity to map; may be null depending on caller
     * @return a {@link RecoveryImageDto} representing the entity
     */
    RecoveryImageDto imageToRecoveryImageDto(Image image);
}