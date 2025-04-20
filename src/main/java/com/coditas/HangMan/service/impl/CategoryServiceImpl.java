package com.coditas.HangMan.service.impl;

import com.coditas.HangMan.entity.Category;
import com.coditas.HangMan.payload.CategoryDto;
import com.coditas.HangMan.repository.CategoryRepository;
import com.coditas.HangMan.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<HashMap<CategoryDto,String>> addCategory(CategoryDto categoryDto) {

        HashMap map=new HashMap();
        if(categoryRepository.findAll().stream().filter(category -> category.getCategoryName().equals(categoryDto.getCategoryName())).collect(Collectors.toList()).size()==0) {
            Category category = new Category();
            category.setCategoryName(categoryDto.getCategoryName().toUpperCase());
            categoryRepository.save(category);

            map.put(category, null);
            return new ResponseEntity<>(map, HttpStatus.CREATED);

        }
        else {
            map.put(new CategoryDto(), "Category with that name already exists");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }


    }

    @Override
    @CrossOrigin
    public CategoryDto deleteCategory(long categoryId) {
        Category category=categoryRepository.findById(categoryId).get();
        category.setDeleted(true);
        categoryRepository.save(category);
        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> showCategories() {
        return categoryRepository.findAll().stream().map(c->mapToDto(c)).collect(Collectors.toList());
    }


    public CategoryDto mapToDto(Category category)
    {
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setDeleted(category.isDeleted());
//        categoryDto.setTournamentList(category.getTournamentList());
        return categoryDto;
    }


    public Category mapToEntity(CategoryDto categoryDto)
    {
        Category category=new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryName(categoryDto.getCategoryName());
        category.setTournamentList(categoryDto.getTournamentList());
        return category;
    }
}
