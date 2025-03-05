package com.mproduits.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mproduits.model.Product;


@Repository
public interface ProductDao extends JpaRepository<Product, Integer>{
}
