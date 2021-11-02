package com.example.rodenstock.service.impl;


import com.example.rodenstock.model.exception.BrandNotFoundException;
import com.example.rodenstock.model.exception.CategoryNotFoundException;
import com.example.rodenstock.model.exception.GlassesNotFoundException;
import com.example.rodenstock.model.Brand;
import com.example.rodenstock.model.Category;
import com.example.rodenstock.model.Glasses;
import com.example.rodenstock.repository.BrandRepository;
import com.example.rodenstock.repository.GlassesRepository;
import com.example.rodenstock.service.GlassesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GlassesServiceImpl implements GlassesService {

    private final GlassesRepository glassesRepository;
    private final BrandRepository brandRepository;

    public GlassesServiceImpl(GlassesRepository glassesRepository, BrandRepository brandRepository) {
        this.glassesRepository = glassesRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Glasses> listAll() {
        return this.glassesRepository.findAll();
    }

    @Override
    public Optional<Glasses> findById(Long id) {
        return this.glassesRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Glasses> save(String imgUrl, Integer price, Integer quantity, String category, Long brand) throws BrandNotFoundException, CategoryNotFoundException {
        Brand b=this.brandRepository.findById(brand).orElseThrow(BrandNotFoundException::new);
        Category cat= Category.findByText(category);
        //this.glassesRepository.deleteByName(name);
        return Optional.of(this.glassesRepository.save(new Glasses(imgUrl, price, quantity, cat, b)));
    }

    @Override
    @Transactional
    public Optional<Glasses> edit(Long id, String imgUrl, Integer price, Integer quantity, String category, Long brand) throws BrandNotFoundException, GlassesNotFoundException {
        Glasses glasses=this.glassesRepository.findById(id).orElseThrow(GlassesNotFoundException::new);
        glasses.setImgUrl(imgUrl);
        glasses.setPrice(price);
        glasses.setQuantity(quantity);

        Brand b=this.brandRepository.findById(brand).orElseThrow(BrandNotFoundException::new);
        glasses.setBrand(b);
        Category cat=Category.findByText(category);
        glasses.setCategory(cat);

        return Optional.of(this.glassesRepository.save(glasses));
    }

    @Override
    public void deleteById(Long id) {
        this.glassesRepository.deleteById(id);
    }


    @Override
    public Glasses get(Long id) {
        return this.glassesRepository.findById(id).get();
    }
}
