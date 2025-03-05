package com.mproduits.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mes-configs")
public class ApplicationPropertiesConfiguration {

	private int limitDePoduits;
	
	public int getLimitDeProduits() {
		return limitDePoduits;
	}
	
	public void setLimitDeProduits(int limitDeProduits) {
		this.limitDePoduits = limitDeProduits;
	}
	
}
