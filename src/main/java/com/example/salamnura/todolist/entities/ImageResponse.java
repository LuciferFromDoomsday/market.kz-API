package com.example.salamnura.todolist.entities;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageResponse {

    public long adsId ;
    public MultipartFile image;

    public ImageResponse() {
    }

    public ImageResponse(long userId, MultipartFile image) {
        this.adsId = userId;
        this.image = image;
    }


    public long getUserId() {
        return adsId;
    }

    public void setUserId(long userId) {
        this.adsId = userId;
    }

    public MultipartFile getImages() {
        return image;
    }

    public void setImages(MultipartFile image) {
        this.image = image;
    }
}
