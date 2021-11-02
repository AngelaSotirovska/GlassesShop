package com.example.rodenstock.service;

import com.example.rodenstock.model.Brand;
import com.example.rodenstock.model.exception.BrandNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<Brand> listAll();

    Optional<Brand> findById(Long id);

    Optional<Brand> save(String name, String country);

    Optional<Brand> edit(Long id, String name, String country);

    void deleteById(Long id);

    void deleteByName(String name);
}
