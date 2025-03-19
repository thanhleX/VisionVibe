package com.cx.visionvibebe.service.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cx.visionvibebe.entity.UploadThumbnail;
import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UploadService {
    private final Cloudinary cloudinary;

    public void uploadThumbnail(UploadThumbnail uploadThumbnail, MultipartFile file) throws IOException {
        if (file == null) {
            throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
        Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String url = uploadResult.get("url").toString();
        String publicId = uploadResult.get("public_id").toString();
        uploadThumbnail.setThumbnailUrl(url);
        uploadThumbnail.setThumbnailPublicId(publicId);
    }

    public void deleteThumbnail(UploadThumbnail uploadThumbnail) throws IOException {
    cloudinary.uploader().destroy(uploadThumbnail.getThumbnailPublicId(), ObjectUtils.emptyMap());
        uploadThumbnail.setThumbnailUrl(null);
        uploadThumbnail.setThumbnailPublicId(null);
    }
}
