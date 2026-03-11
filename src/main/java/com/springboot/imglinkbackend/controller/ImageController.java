package com.springboot.imglinkbackend.controller;


import com.springboot.imglinkbackend.dto.ImageUploadRequestDTO;
import com.springboot.imglinkbackend.dto.ImageUploadResponseDTO;
import com.springboot.imglinkbackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private final ImageService imageService;

    @PostMapping("/upload")
    public ImageUploadResponseDTO uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

        ImageUploadRequestDTO requestDTO = new ImageUploadRequestDTO();
        requestDTO.setImage(file);

        return imageService.uploadImage(requestDTO);
    }
}
