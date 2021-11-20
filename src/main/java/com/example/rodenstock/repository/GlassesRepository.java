package com.example.rodenstock.repository;

import com.example.rodenstock.model.Category;
import com.example.rodenstock.model.Glasses;
import com.example.rodenstock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlassesRepository extends JpaRepository<Glasses, Long> {

    void deleteById(Long id);
}
