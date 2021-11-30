package com.example.rodenstock.service;

import com.example.rodenstock.model.Brand;
import com.example.rodenstock.model.Category;
import com.example.rodenstock.model.User;
import com.example.rodenstock.model.exception.BrandNotFoundException;
import com.example.rodenstock.model.exception.CategoryNotFoundException;
import com.example.rodenstock.model.exception.GlassesNotFoundException;
import com.example.rodenstock.model.Glasses;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface GlassesService {
    List<Glasses> listAll();

    Optional<Glasses> findById(Long id);

    Optional<Glasses> save(String imgUrl, Integer price, Integer quantity, String category, Long brand) throws BrandNotFoundException, CategoryNotFoundException;

    Optional<Glasses> edit(Long id, String imgUrl, Integer price, Integer quantity, String category, Long brand) throws BrandNotFoundException, GlassesNotFoundException;

    void deleteById(Long id);

    Glasses get(Long id);

}
