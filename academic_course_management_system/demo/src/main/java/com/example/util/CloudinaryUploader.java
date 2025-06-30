package com.example.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CloudinaryUploader {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryUploader.class);

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Upload file lên Cloudinary và trả về URL PDF hợp lệ
     */
    public String uploadFile(MultipartFile file) {
        logger.info("Uploading file to Cloudinary with api_key: {}", cloudinary.config.apiKey);
        try {
            Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto")  
            );
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload file lên Cloudinary: " + e.getMessage(), e);
        }
    }

    /**
     * Xóa file khỏi Cloudinary
     */
    public void deleteFile(String url) {
        try {
            String publicId = extractPublicIdFromUrl(url);
            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "raw"));
            logger.info("Deleted file from Cloudinary: {}", publicId);
        } catch (IOException e) {
            logger.warn("Không thể xóa file từ Cloudinary: {}", e.getMessage());
        }
    }

    private String extractPublicIdFromUrl(String url) {
        String regex = "https://res\\.cloudinary\\.com/[^/]+/raw/upload/v\\d+/(.+?)(\\.[^/.]+)?$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Invalid Cloudinary URL: " + url);
    }
}
