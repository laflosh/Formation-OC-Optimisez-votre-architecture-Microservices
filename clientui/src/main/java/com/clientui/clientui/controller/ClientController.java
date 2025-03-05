package com.clientui.clientui.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clientui.clientui.beans.CommandeBean;
import com.clientui.clientui.beans.PaiementBean;
import com.clientui.clientui.beans.ProductBean;
import com.clientui.clientui.proxy.MicroserviceCommandeProxy;
import com.clientui.clientui.proxy.MicroservicePaiementProxy;
import com.clientui.clientui.proxy.MicroserviceProductProxy;

@Controller
public class ClientController {

	@Autowired
	private MicroserviceProductProxy productProxy;
	
	@Autowired
	private MicroservicePaiementProxy paiementProxy;
	
	@Autowired
	private MicroserviceCommandeProxy commandeProxy;
	
	@RequestMapping("/")
	public String accueil(Model model) {

		List<ProductBean> produits = productProxy.listeDesProduits();
		
		model.addAttribute("produits", produits);
		
		return"Accueil";

	}
	
	@RequestMapping("/details-produit/{id}")
	public String ficheProduit(@PathVariable int id, Model model) {
		
		ProductBean produit = productProxy.recupererUnProduit(id);
		
		System.out.println(produit);
		
		model.addAttribute("produit", produit);
		
		return"FicheProduit";
		
	}
	
	@RequestMapping("/details-produit/commander-produit/{idProduit}/{montant}")
	public String passerCommande(@PathVariable("idProduit") int idProduit, @PathVariable("montant") Double montant, Model model) {
		
		CommandeBean commande = new CommandeBean();
		
        commande.setProductId(idProduit);
        commande.setQuantite(1);
        commande.setDateCommande(new Date());
        
        CommandeBean commandeAjoutee = commandeProxy.ajouterCommande(commande);
        
        model.addAttribute("commande", commandeAjoutee);
        model.addAttribute("montant", montant);
        
        return"Paiement";
		
	}
	
	@RequestMapping("/payer-commande/{idCommande}/{montantCommande}")
	public String payerCommande(@PathVariable("idCommande")int idCommande, @PathVariable("montantCommande") Double montantCommande, Model model) {
		
		PaiementBean paiementAExcecuter = new PaiementBean();
		
        paiementAExcecuter.setIdCommande(idCommande);
        paiementAExcecuter.setMontant(montantCommande);
        paiementAExcecuter.setNumeroCarte(numcarte()); // on génère un numéro au hasard pour simuler une CB
        
        ResponseEntity<PaiementBean> paiement = paiementProxy.payerUneCommande(paiementAExcecuter);
        
        Boolean paiementAccepte = false;
        
        if(paiement.getStatusCode() == HttpStatus.CREATED)
            paiementAccepte = true;

        model.addAttribute("paiementOk", paiementAccepte); // on envoi un Boolean paiementOk à la vue

        return "Confirmation";
		
	}
	
    //Génére une serie de 16 chiffres au hasard pour simuler vaguement une CB
    private Long numcarte() {

        return ThreadLocalRandom.current().nextLong(1000000000000000L,9000000000000000L );
    }

}
