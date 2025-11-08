package br.com.webpanel.deploy.images;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import br.com.webpanel.deploy.images.dto.CreateImageDto;
import br.com.webpanel.deploy.images.dto.RecoveryImageDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/image", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<RecoveryImageDto> createImage(@RequestBody CreateImageDto dto) {
        return new ResponseEntity<>(imageService.createImage(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RecoveryImageDto>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecoveryImageDto> getImageById(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecoveryImageDto> updateImage(@PathVariable Long id, @RequestBody CreateImageDto dto) {
        return ResponseEntity.ok(imageService.updateImage(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}