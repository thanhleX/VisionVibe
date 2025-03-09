package com.cx.visionvibe.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String username;
    String password;
    String rePassword;
    String fullName;
    String email;
    String phone;
    MultipartFile image;
    Set<Long> roleId;
}
