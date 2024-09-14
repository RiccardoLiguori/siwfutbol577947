package it.uniroma3.siwprogetto.validator;

import it.uniroma3.siwprogetto.model.Presidente;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Presidente.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof Presidente) {
            Presidente presidente = (Presidente) target;

            // Validazione del codice fiscale
            if (presidente.getCodiceFiscale() == null || presidente.getCodiceFiscale().length() != 16) {
                errors.rejectValue("codiceFiscale", "codiceFiscale.invalid", "Il codice fiscale deve essere di 16 caratteri");
            }

            // Validazione del nome
            if (presidente.getNome().length()<3) {
                errors.rejectValue("nome", "nome.tooShort", "Il nome deve essere di almeno 3 caratteri");
            }

            // Validazione del cognome
            if (presidente.getCognome().length()<3) {
                errors.rejectValue("cognome", "cognome.tooShort", "Il cognome deve essere di almeno 3 caratteri");
            }

            // Validazione della data di nascita
            if (presidente.getDataNascita() == null) {
                errors.rejectValue("dataNascita", "dataNascita.invalid", "La data di nascita non può essere vuota");
            } else {
                // Se vuoi, puoi aggiungere una validazione per assicurarti che l'utente sia maggiorenne
                if (presidente.getDataNascita().isAfter(LocalDate.now().minusYears(18))) {
                    errors.rejectValue("dataNascita", "dataNascita.invalid", "Il presidente deve essere maggiorenne");
                }
            }

            // Validazione del luogo di nascita
            if (StringUtils.isBlank(presidente.getLuogoNascita())) {
                errors.rejectValue("luogoNascita", "luogoNascita.invalid", "Il luogo di nascita non può essere vuoto");
            }

            // Altre validazioni personalizzate per il presidente, se necessarie
        }
    }
}
