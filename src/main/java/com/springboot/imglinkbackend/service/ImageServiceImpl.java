package com.springboot.imglinkbackend.service;

import com.springboot.imglinkbackend.dto.ImageUploadRequestDTO;
import com.springboot.imglinkbackend.dto.ImageUploadResponseDTO;
import com.springboot.imglinkbackend.entity.ImageFile;
import com.springboot.imglinkbackend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final String UPLOAD_DIR = "uploads/";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ImageUploadResponseDTO uploadImage(ImageUploadRequestDTO request) throws IOException {

        MultipartFile file = request.getImage();
        String originalName = file.getOriginalFilename();

        if (originalName == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }

        if (!(originalName.endsWith(".jpg") || originalName.endsWith(".png") || originalName.endsWith(".webp"))) {
            throw new IllegalArgumentException("Only JPG, PNG, or WEBP files are allowed.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds 5MB limit.");
        }

        originalName = Paths.get(originalName).getFileName().toString();

        String extension = originalName.substring(originalName.lastIndexOf("."));
        String fileName = UUID.randomUUID() + extension;

//        Path path = Paths.get(UPLOAD_DIR + fileName);
        Path path = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes(), StandardOpenOption.CREATE);

        String url = "http://localhost:8080/images/" + fileName;

        ImageFile image =  new ImageFile();
        image.setFileName(fileName);
        image.setUrl(url);
        image.setSize(String.valueOf(file.getSize()));

        imageRepository.save(image);

        return new ImageUploadResponseDTO(fileName, url, file.getSize());
    }
}
