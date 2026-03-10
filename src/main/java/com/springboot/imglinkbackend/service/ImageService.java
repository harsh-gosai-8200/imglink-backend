package com.springboot.imglinkbackend.service;

import com.springboot.imglinkbackend.dto.ImageUploadRequestDTO;
import com.springboot.imglinkbackend.dto.ImageUploadResponseDTO;

import java.io.IOException;

public interface ImageService {

    ImageUploadResponseDTO uploadImage(ImageUploadRequestDTO request) throws IOException;

}
