package br.com.webpanel.deploy.images;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.webpanel.deploy.images.dto.CreateImageDto;
import br.com.webpanel.deploy.images.dto.RecoveryImageDto;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    Image createImageDtoToImage(CreateImageDto dto);
    RecoveryImageDto imageToRecoveryImageDto(Image image);
}