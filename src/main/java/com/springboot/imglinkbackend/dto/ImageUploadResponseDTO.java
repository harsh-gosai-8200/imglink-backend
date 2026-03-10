package com.springboot.imglinkbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageUploadResponseDTO {

    private String fileName;

    private String url;

    private long size;

}
