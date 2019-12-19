package com.hanyin.website.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class FunctionService {

    private UserService userService;

    public FunctionService(UserService userService) {
        this.userService = userService;
    }

    public String sessionCheck(String sessionId, Supplier<String> responseSupplier) {
        String response;
        Integer userId = userService.loginCheck(sessionId);
        if (userId == null) {
            Response<String> stringResponse = new Response<>();
            stringResponse.setErrorMessage("session check failed!");
            response = stringResponse.response();
        } else response = responseSupplier.get();
        return response;
    }

    public String generalImageUpload(String sessionId, MultipartFile image, Function<String, String> callback) {
        return sessionCheck(sessionId, () -> {
            String fileName = UUID.randomUUID().toString();
            String imageUrl = "upload/" + fileName;
            File file = new File(imageUrl);
            try {
                image.transferTo(file);
                return callback.apply(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                Response<String> response = new Response<>();
                response.setErrorMessage("图片上传出错！");
                return response.response();
            }
        });
    }
}
