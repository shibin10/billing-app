package com.app.billingapi.service;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;


@Component
public class FileStorageService {

    @Value("${file.upload.base-path}")
    private String basePath;

    public String saveImage(MultipartFile file, String subFolder) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        // Create subdirectory under base path
        Path folderPath = Paths.get(basePath, subFolder);
        Files.createDirectories(folderPath);

        // Generate unique filename
        String ext = Optional.ofNullable(FilenameUtils.getExtension(file.getOriginalFilename()))
                             .orElse("jpg");
        String filename = UUID.randomUUID().toString() + "." + ext;

        // Save file
        Path fullPath = folderPath.resolve(filename);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, fullPath, StandardCopyOption.REPLACE_EXISTING);
        }

        // Return path relative to base (e.g. "shops/uuid.png")
        return subFolder + "/" + filename;
    }
}

