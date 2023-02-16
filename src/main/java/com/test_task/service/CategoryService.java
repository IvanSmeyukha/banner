package com.test_task.service;

import com.test_task.entity.Banner;
import com.test_task.entity.Category;
import com.test_task.repository.BannerRepository;
import com.test_task.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BannerRepository bannerRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, BannerRepository bannerRepository) {
        this.categoryRepository = categoryRepository;
        this.bannerRepository = bannerRepository;
    }


    public Optional<String> saveCategory(Category category){
        if (category.getId() == null) {
            if(!categoryRepository.existsByNameAndDeletedFalse(category.getName())){
                categoryRepository.save(category);
                return Optional.empty();
            }
            return Optional.of("Category with Name \"" + category.getName() + "\" is already exist");
        }
        if (categoryRepository.existsByIdAndDeletedFalse(category.getId())) {
            if(!categoryRepository.existsByNameAndIdIsNotAndDeletedFalse(category.getName(), category.getId())){
                categoryRepository.save(category);
                return Optional.empty();
            }
            return Optional.of("Category with Name \"" + category.getName() + "\" is already exist");
        }
        return Optional.of("Category with ID \"" + category.getId() + "\" does not exist");
    }

    public Optional<String> deleteCategory(int id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            List<Banner> bannerList = bannerRepository.findAllByCategoryIsAndDeletedFalse(category);
            if(bannerList.isEmpty()){
                category.setDeleted(true);
                categoryRepository.save(category);
                return Optional.empty();
            }
            List<Integer> bannersIds = bannerList.stream().map(Banner::getId).toList();
            return Optional.of("The category cannot be deleted. ID of banners linked to it: " + bannersIds);
        }
        return Optional.of("Category with ID \"" + id + "\" does not exist");
    }

    public List<Category> getCategoriesList(String name) {
        return categoryRepository.findByNameContainingIgnoreCaseAndDeletedIsFalse(name);
    }
}
