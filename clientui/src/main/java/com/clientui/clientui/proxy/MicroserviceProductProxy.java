package com.clientui.clientui.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.clientui.clientui.beans.ProductBean;

@Component
@FeignClient(name = "microservice-produits", url = "localhost:9001", dismiss404 = true)
public interface MicroserviceProductProxy {

	@GetMapping(value = "/Produits")
	List<ProductBean> listeDesProduits();
	
	@GetMapping(value = "/Produits/{id}")
	ProductBean recupererUnProduit(@PathVariable("id") int id);
	
}
