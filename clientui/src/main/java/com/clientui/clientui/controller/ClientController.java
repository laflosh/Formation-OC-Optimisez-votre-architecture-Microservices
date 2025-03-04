package com.clientui.clientui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clientui.clientui.beans.ProductBean;
import com.clientui.clientui.proxy.MicroserviceProductProxy;

@Controller
public class ClientController {

	@Autowired
	private MicroserviceProductProxy productProxy;
	
	@RequestMapping("/")
	public String accueil(Model model) {

		List<ProductBean> produits = productProxy.listeDesProduits();
		
		model.addAttribute("produits", produits);
		
		return"Accueil";

	}

}
