package com.mpaiement.web.controller;

import com.mpaiement.beans.CommandeBean;
import com.mpaiement.dao.PaiementDao;
import com.mpaiement.model.Paiement;
import com.mpaiement.proxies.MicroserviceCommandeProxy;
import com.mpaiement.web.exceptions.PaiementExistantException;
import com.mpaiement.web.exceptions.PaiementImpossibleException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaiementController {

    @Autowired
    PaiementDao paiementDao;
    
    @Autowired
    MicroserviceCommandeProxy microserviceCommandeProxy;

    @PostMapping(value = "/paiement")
    public ResponseEntity<Paiement>  payerUneCommande(@RequestBody Paiement paiement){


        //Vérifions s'il y a déjà un paiement enregistré pour cette commande
        Paiement paiementExistant = paiementDao.findByidCommande(paiement.getIdCommande());
        if(paiementExistant != null) throw new PaiementExistantException("Cette commande est déjà payée");

        //Enregistrer le paiement
        Paiement nouveauPaiement = paiementDao.save(paiement);
        if(nouveauPaiement == null) throw new PaiementImpossibleException("Erreur, impossible d'établir le paiement, réessayez plus tard");

        //TODO Nous allons appeler le Microservice Commandes ici pour lui signifier que le paiement est accepté

        Optional<CommandeBean> commandeReq = microserviceCommandeProxy.recupererUneCommande(paiement.getId());
        
        //.get() sort de l'optionnal
        CommandeBean commande = commandeReq.get();
        
        commande.setCommandePayee(true);
        
        microserviceCommandeProxy.updateCommande(commande);
        
        return new ResponseEntity<Paiement>(nouveauPaiement, HttpStatus.CREATED);

    }




}
