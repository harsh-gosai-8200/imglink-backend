package com.springboot.imglinkbackend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadRequestDTO {

    private MultipartFile image;

}
