package com.ziker0k.http.service;

import com.ziker0k.http.util.PropertiesUtil;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class ImageService {
    private static final ImageService INSTANCE = new ImageService();

    private final String basePath = PropertiesUtil.get("image.base.url");

    @SneakyThrows
    public void uploadImage(String imagePath, InputStream imageContent) {
        var imageFullPath = Path.of(basePath, imagePath);
        if (!Files.exists(imageFullPath)) {
            try (imageContent) {
                Files.createDirectories(imageFullPath.getParent());
                Files.write(imageFullPath, imageContent.readAllBytes(), CREATE, TRUNCATE_EXISTING);
            }
        }
    }

    @SneakyThrows
    public Optional<InputStream> getImage(String imagePath) {
        var imageFullPath = Path.of(basePath, imagePath);
        return Files.exists(imageFullPath) ? Optional.of(Files.newInputStream(imageFullPath)) : Optional.empty();
    }

    public static ImageService getInstance() {
        return INSTANCE;
    }
}
