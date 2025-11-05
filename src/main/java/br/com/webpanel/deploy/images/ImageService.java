package br.com.webpanel.deploy.images;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.webpanel.deploy.images.dto.CreateImageDto;
import br.com.webpanel.deploy.images.dto.RecoveryImageDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public RecoveryImageDto createImage(CreateImageDto dto) {
        Image image = imageMapper.createImageDtoToImage(dto);
        Image savedImage = imageRepository.save(image);
        return imageMapper.imageToRecoveryImageDto(savedImage);
    }

    public List<RecoveryImageDto> getAllImages() {
        return imageRepository.findAll()
            .stream()
            .map(imageMapper::imageToRecoveryImageDto)
            .collect(Collectors.toList());
    }

    public RecoveryImageDto getImageById(Long id) {
        Image image = imageRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + id));
        return imageMapper.imageToRecoveryImageDto(image);
    }

    public RecoveryImageDto updateImage(Long id, CreateImageDto dto) {
        Image image = imageRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + id));
        
        image.setName(dto.name());
        image.setBase64(dto.base64());
        image.setSize(dto.size());

        Image updatedImage = imageRepository.save(image);
        return imageMapper.imageToRecoveryImageDto(updatedImage);
    }

    public void deleteImage(Long id) {
        if (!imageRepository.existsById(id)) {
            throw new EntityNotFoundException("Image not found with id: " + id);
        }
        imageRepository.deleteById(id);
    }
}