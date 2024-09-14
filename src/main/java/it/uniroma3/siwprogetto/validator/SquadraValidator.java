package it.uniroma3.siwprogetto.validator;

import it.uniroma3.siwprogetto.model.Squadra;
import it.uniroma3.siwprogetto.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Component
public class SquadraValidator implements Validator {

    @Autowired
    private SquadraService squadraService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Squadra.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof Squadra) {
            Squadra squadra = (Squadra) target;

            // Validazione del nome
            if (StringUtils.isBlank(squadra.getNome())) {
                errors.rejectValue("nome", "nome.invalid", "Il nome non può essere vuoto");
            } else
                if (squadra.getNome().length() < 4) {
                    errors.rejectValue("nome", "nome.tooShort", "Il nome deve essere di almeno 4 caratteri");
                } else
                    if (squadraService.findByName(squadra.getNome()) != null) {
                        errors.rejectValue("nome", "nome.duplicate", "La Squadra è gia stata inserita");
                    }

                // Validazione dell'anno di fondazione
                if (squadra.getAnnoFondazione() == null) {
                    errors.rejectValue("annoFondazione", "annoFondazione.invalid", "L'anno di fondazione non può essere vuoto");
                } else if (squadra.getAnnoFondazione().isAfter(LocalDate.now())) {
                    errors.rejectValue("annoFondazione", "annoFondazione.invalid", "L'anno di fondazione non può essere nel futuro");
                } else {
                    if (squadra.getAnnoFondazione().isBefore(LocalDate.now().minusYears(124))) {
                        errors.rejectValue("annoFondazione", "annoFondazione.invalid", "L'anno di fondazione non può essere anteriore al 1900");
                    }
                }

                // Validazione dell'indirizzo della sede
                if (StringUtils.isBlank(squadra.getIndirizzoSede())) {
                    errors.rejectValue("indirizzoSede", "indirizzoSede.invalid", "L'indirizzo della sede non può essere vuoto");
                }


                // Validazione del presidente
                if (squadra.getPresidente() == null) {
                    errors.rejectValue("presidente", "presidente.invalid", "La squadra deve avere un presidente");
                }

            }
        }
    }
