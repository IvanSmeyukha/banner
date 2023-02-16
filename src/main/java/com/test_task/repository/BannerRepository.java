package com.test_task.repository;

import com.test_task.entity.Banner;
import com.test_task.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {
    boolean existsByIdAndDeletedFalse(Integer id);

    boolean existsByNameAndDeletedFalse(String name);

    boolean existsByNameAndIdIsNotAndDeletedFalse(String name, Integer id);

    List<Banner> findAllByNameContainingIgnoreCaseAndDeletedIsFalse(String name);

    List<Banner> findByCategory_ReqNameAndDeletedIsFalseOrderByPriceDesc(String reqName);

    List<Banner> findAllByCategoryIsAndDeletedFalse(Category category);
}
