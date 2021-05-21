package com.example.salamnura.todolist.services;

import com.example.salamnura.todolist.entities.AdType;
import com.example.salamnura.todolist.entities.Ads;
import com.example.salamnura.todolist.entities.Brand;
import com.example.salamnura.todolist.entities.Image;

import java.util.List;

public interface AdsService {

    // Ads methods
    Ads addAds(Ads ads);
    List<Ads> getAllAds();
    List<Ads> getAdsByAdTypeId(Long id);
    List<Ads> getAdsByBrandId(Long id);
    List<Ads> getAdsByUserId(Long id);
    Ads getAdsByName(String name);
    Ads getAds(Long id);
    Ads saveAds(Ads ads);
    void deleteAds(Ads ads);


    // AdType methods

    AdType addAdType(AdType adType);
    List<AdType> getAllAdTypes();
    AdType getAdType(Long id);
    void deleteAdType(AdType adType);

    // Image methods
    Image addImage(Image image);
    void deleteImage(Image image);
    Image saveImage(Image image);
    List<Image> getAdsImage(Long id);


    //Brand methods

    Brand addBrand(Brand brand);
    List<Brand> getAllBrand();
    Brand getBrand(Long id);
    void deleteBrand(Brand brand);



}
