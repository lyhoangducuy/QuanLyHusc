package com.example.quanlyhusc.service.uploadfile;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();
    String store(MultipartFile file, String folder);
    void delete(String url);
}