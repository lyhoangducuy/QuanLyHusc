package com.example.quanlyhusc.service.uploadfile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path baseRoot;

    public FileSystemStorageService() {
        this.baseRoot = Paths.get("uploads");
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(baseRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String store(MultipartFile file, String folder) {
        try {
            Path rootLocation = baseRoot.resolve(folder);
            Files.createDirectories(rootLocation);

            String original = file.getOriginalFilename();
            String ext = "";
            int dot = original.lastIndexOf('.');

            String newName = System.currentTimeMillis() + "-" + UUID.randomUUID() + ext;

            Path destinationFile = rootLocation.resolve(newName).normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return "/uploads/" + folder + "/" + newName;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String url) {
        try {
           

            String normalized = url.trim();
            if (normalized.startsWith("/")) normalized = normalized.substring(1);
            if (!normalized.startsWith("uploads/")) return;

            // cắt "uploads/" để ra <folder>/<file>
            String relative = normalized.substring("uploads/".length());

            Path filePath = baseRoot.resolve(relative).normalize().toAbsolutePath();

            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
