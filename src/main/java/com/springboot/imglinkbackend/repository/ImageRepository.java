package com.springboot.imglinkbackend.repository;

import com.springboot.imglinkbackend.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageFile, Long> {
}
