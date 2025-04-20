package com.coditas.HangMan.repository;

import com.coditas.HangMan.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    public Category findByCategoryName(String categoryName);
}
