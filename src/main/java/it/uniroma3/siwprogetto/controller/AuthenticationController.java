package it.uniroma3.siwprogetto.controller;

import it.uniroma3.siwprogetto.model.Presidente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwprogetto.model.Credentials;
import it.uniroma3.siwprogetto.service.CredentialsService;
import it.uniroma3.siwprogetto.service.PresidenteService;
import it.uniroma3.siwprogetto.validator.CredentialsValidator;
import it.uniroma3.siwprogetto.validator.UserValidator;
import org.springframework.validation.BindingResult;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

    @Autowired
	private PresidenteService presidenteService;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private UserValidator userValidator;

	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("presidente", new Presidente());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser";
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin";
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/indexAdmin.html";
			}
		}
        return "index.html";
	}
		
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/indexAdmin.html";
        }
        return "index.html";
    }


	@PostMapping("/register")
	public String registerUser(@ModelAttribute("presidente") Presidente presidente,
							   BindingResult presidenteBindingResult,
							   @ModelAttribute("credentials") Credentials credentials,
							   BindingResult credentialsBindingResult,
							   Model model) {

		// Usa il validator per validare presidente e credentials
		userValidator.validate(presidente, presidenteBindingResult);
		credentialsValidator.validate(credentials, credentialsBindingResult);



		// Se ci sono errori, ritorna alla form di registrazione con gli errori
		if (presidenteBindingResult.hasErrors() || credentialsBindingResult.hasErrors()) {
			return "formRegisterUser";
		}

		// Salva il Presidente e le Credenziali
		presidenteService.savePresidente(presidente);
		credentials.setPresidente(presidente);
		credentialsService.saveCredentials(credentials);
		model.addAttribute("presidente", presidente);

		// Reindirizza alla pagina di conferma della registrazione
		return "registrationSuccessful";
	}




}
    
