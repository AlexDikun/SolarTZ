package su.solyara.Congratulator.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class PhotoController {

    @GetMapping("/photos/{fileName:.+}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String fileName) throws IOException {
        Path filePath = Paths.get("uploads/photos").resolve(fileName);
        if (Files.exists(filePath)) {
            byte[] photoBytes = Files.readAllBytes(filePath);
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(photoBytes);
        }

        Resource resource = new ClassPathResource("static/images/" + fileName);
        if (resource.exists()) {
            byte[] photoBytes = resource.getInputStream().readAllBytes();
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(photoBytes);
        }
        
        return ResponseEntity.notFound().build();
    }
}

