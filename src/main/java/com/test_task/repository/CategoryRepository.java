package com.test_task.repository;

import com.test_task.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByIdAndDeletedFalse(Integer id);

    boolean existsByNameAndDeletedFalse(String name);

    boolean existsByNameAndIdIsNotAndDeletedFalse(String name, Integer id);


    List<Category> findByNameContainingIgnoreCaseAndDeletedIsFalse(String name);
}
