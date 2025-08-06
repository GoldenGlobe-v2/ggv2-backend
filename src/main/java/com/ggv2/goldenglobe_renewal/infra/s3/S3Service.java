package com.ggv2.goldenglobe_renewal.infra.s3;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

  private final S3Template s3Template;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  public String upload(MultipartFile multipartFile, String dir) throws IOException {
    String originalFilename = multipartFile.getOriginalFilename();
    String extension = "";
    if (originalFilename != null && originalFilename.contains(".")) {
      extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    String randomFileName = UUID.randomUUID() + extension;
    String key = dir + "/" + randomFileName;

    return s3Template.upload(bucket, key, multipartFile.getInputStream()).getURL().toString();
  }
}