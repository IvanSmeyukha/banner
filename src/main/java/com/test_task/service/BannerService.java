package com.test_task.service;

import com.test_task.entity.Banner;
import com.test_task.repository.BannerRepository;
import com.test_task.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public BannerService(BannerRepository bannerRepository, CategoryRepository categoryRepository) {
        this.bannerRepository = bannerRepository;
        this.categoryRepository = categoryRepository;
    }


    public Optional<String> saveBanner(Banner banner) {
        if (!categoryRepository.existsByIdAndDeletedFalse(banner.getCategory().getId()))
            return Optional.of("Category with ID \"" + banner.getCategory().getId() + "\" does not exist");
        if (banner.getId() == null) {
            if(!bannerRepository.existsByNameAndDeletedFalse(banner.getName())){
                bannerRepository.save(banner);
                return Optional.empty();
            }
            return Optional.of("Banner with Name \"" + banner.getName() + "\" is already exist");
        }
        if (bannerRepository.existsByIdAndDeletedFalse(banner.getId())) {
            if(!bannerRepository.existsByNameAndIdIsNotAndDeletedFalse(banner.getName(), banner.getId())){
                bannerRepository.save(banner);
                return Optional.empty();
            }
            return Optional.of("Banner with Name \"" + banner.getName() + "\" is already exist");
        }
        return Optional.of("Banner with ID \"" + banner.getId() + "\" does not exist");
    }

    public Optional<String> deleteBanner(int id) {
        Optional<Banner> optional = bannerRepository.findById(id);
        if (optional.isPresent()) {
            Banner banner = optional.get();
            banner.setDeleted(true);
            bannerRepository.save(banner);
            return Optional.empty();
        }
        return Optional.of("Banner with such ID does not exist");
    }

    public List<Banner> getBannersList(String name) {
        return bannerRepository.findAllByNameContainingIgnoreCaseAndDeletedIsFalse(name);
    }

}
