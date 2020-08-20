package com.hashicorp.vault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class SecretgrabberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretgrabberApplication.class, args);
	}

}

@RestController
class SecretGrabberController {
    
    @RequestMapping("/{app}")
    String strapp(@PathVariable String app){
    	
    	String secretPath = "secret/";
        
    	// (1) Instantiate an object that contains the Vault Configuration 
    	AbstractVaultConfiguration vaultConfig = new AppConfiguration();
    	
    	// (2) Pass the Vault Configuration to the Vault Template Object
    	VaultTemplate vaultTemplate = new VaultTemplate(vaultConfig.vaultEndpoint(),vaultConfig.clientAuthentication());
   
    	// (3) Vault Template Object will read the secret using secretPath.  The secrets will be placed 
    	// in the Secrets object
    	VaultResponseSupport<Secrets> response = vaultTemplate.read(secretPath + app, Secrets.class);
          	
    	// (4) Returns the secrets
    	return "App: " + app + "<br><u><Secrets</u><br>Username: " + response.getData().getUsername();
    }
}