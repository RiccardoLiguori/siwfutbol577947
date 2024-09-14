package it.uniroma3.siwprogetto.validator;

import it.uniroma3.siwprogetto.model.Credentials;
import it.uniroma3.siwprogetto.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CredentialsValidator implements Validator {

    @Autowired
    private CredentialsService credentialsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Credentials.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target instanceof Credentials) {
            Credentials credentials = (Credentials) target;

            // Verifica che l'username non sia già in uso
            if (credentialsService.getCredentials(credentials.getUsername()) != null) {
                errors.rejectValue("username", "username.duplicate", "L'username è gia in uso");
            }
            if (credentials.getUsername().length() <5 ) {
                errors.rejectValue("username", "username.tooShort","L'username deve essere di almeno 5 caratteri" );
            }

            // Validazione della password (ad esempio, lunghezza minima)
            if (credentials.getPassword().length() < 5) {
                errors.rejectValue("password", "password.tooShort", "La password deve essere di almeno 5 caratteri");
            }

            // Altre validazioni personalizzate per le credenziali
            // ...
        }
    }
}
