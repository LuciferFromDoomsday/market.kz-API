package com.example.salamnura.todolist.services.impl;

import com.example.salamnura.todolist.entities.AdType;
import com.example.salamnura.todolist.entities.Ads;
import com.example.salamnura.todolist.entities.Brand;
import com.example.salamnura.todolist.entities.Image;
import com.example.salamnura.todolist.repositories.AdsRepository;
import com.example.salamnura.todolist.repositories.AdsTypeRepository;
import com.example.salamnura.todolist.repositories.BrandRepository;
import com.example.salamnura.todolist.repositories.ImageRepository;
import com.example.salamnura.todolist.services.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdsServiceImpl implements AdsService {

    @Autowired
    private AdsRepository adsRepository;

    @Autowired
    private AdsTypeRepository adsTypeRepository;

    @Autowired
   private ImageRepository imageRepository;

    @Autowired
    private BrandRepository brandRepository;




    @Override
    public Ads addAds(Ads ads) {
        return adsRepository.save(ads);
    }

    @Override
    public List<Ads> getAllAds() {
        return adsRepository.findAll();
    }

    @Override
    public List<Ads> getAdsByAdTypeId(Long id) {
        return adsRepository.findAllByAdTypeId(id);
    }

    @Override
    public List<Ads> getAdsByBrandId(Long id) {
        return adsRepository.findAllByBrandId(id);
    }

    @Override
    public List<Ads> getAdsByUserId(Long id) {
        return adsRepository.findAllByUserId(id);
    }

    @Override
    public Ads getAdsByName(String name) {
        return adsRepository.findByName(name);
    }

    @Override
    public Ads getAds(Long id) {
        return adsRepository.getOne(id);
    }

    @Override
    public Ads saveAds(Ads ads) {
        return adsRepository.save(ads);
    }

    @Override
    public void deleteAds(Ads ads) {
        adsRepository.delete(ads);
    }

    @Override
    public AdType addAdType(AdType adType) {
        return adsTypeRepository.save(adType);
    }

    @Override
    public List<AdType> getAllAdTypes() {
        return adsTypeRepository.findAll();
    }

    @Override
    public AdType getAdType(Long id) {
        return adsTypeRepository.getOne(id);
    }

    @Override
    public void deleteAdType(AdType adType) {
     adsTypeRepository.delete(adType);
    }

    @Override
    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(Image image) {
    imageRepository.delete(image);
    }

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public List<Image> getAdsImage(Long id) {
        return imageRepository.getAllByAdsId(id);
    }

    @Override
    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrand(Long id) {
        return brandRepository.getOne(id);
    }

    @Override
    public void deleteBrand(Brand brand) {
        brandRepository.delete(brand);
    }
}
