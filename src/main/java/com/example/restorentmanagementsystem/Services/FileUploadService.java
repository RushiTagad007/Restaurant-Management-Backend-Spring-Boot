package com.example.restorentmanagementsystem.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service @RequiredArgsConstructor
@Transactional
public class FileUploadService {
    public String uploadFile(MultipartFile file) throws RuntimeException, IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dzavgoc9w",
                "api_key", "842688657531372",
                "api_secret", "-djtDm1NRXVtjZ3L-HGaLfYnNBw",
                "secure", true));
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return   uploadResult.get("url").toString();

    }

}
