package com.coditas.HangMan.service.interfaces;

import com.coditas.HangMan.entity.Category;
import com.coditas.HangMan.payload.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public interface CategoryService {

    public ResponseEntity<HashMap<CategoryDto,String>> addCategory(CategoryDto categoryDto);
    public CategoryDto deleteCategory(long categoryId);
    public List<CategoryDto> showCategories();

}
