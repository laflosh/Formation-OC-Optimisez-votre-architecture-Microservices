package com.mproduits.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mproduits.configuration.ApplicationPropertiesConfiguration;
import com.mproduits.dao.ProductDao;
import com.mproduits.model.Product;
import com.mproduits.web.exceptions.ProductNotFoundException;

@RestController
public class ProductController {

    @Autowired
    ProductDao productDao;
    
    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    // Affiche la liste de tous les produits disponibles
    @GetMapping(value = "/Produits")
    public List<Product> listeDesProduits(){

        List<Product> products = productDao.findAll();

        if(products.isEmpty()) {
			throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");
		}
        
        List<Product> listeLimitee = products.subList(0, appProperties.getLimitDeProduits());

        return listeLimitee;

    }

    //Récuperer un produit par son id
    @GetMapping( value = "/Produits/{id}")
    public Optional<Product> recupererUnProduit(@PathVariable int id) {

        Optional<Product> product = productDao.findById(id);

        if(!product.isPresent()) {
			throw new ProductNotFoundException("Le produit correspondant à l'id " + id + " n'existe pas");
		}

        return product;
    }
}

