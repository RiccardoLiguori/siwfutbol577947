package it.uniroma3.siwprogetto.controller;

import it.uniroma3.siwprogetto.model.Credentials;
import it.uniroma3.siwprogetto.model.Giocatore;
import it.uniroma3.siwprogetto.model.GiocatoreTesserato;
import it.uniroma3.siwprogetto.model.Squadra;
import it.uniroma3.siwprogetto.service.*;
import it.uniroma3.siwprogetto.validator.GiocatoreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class GiocatoreController {

    @Autowired
    private SquadraService squadraService;

    @Autowired
    private PresidenteService presidenteService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private GiocatoreService giocatoreService;

    @Autowired
    private GiocatoreTesseratoService giocatoreTesseratoService;

    @Autowired
    private GiocatoreValidator giocatoreValidator;

    @GetMapping("/gestisciSquadra")
    public String gestisciSquadra(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        Squadra squadra = squadraService.findByPresidente(credentials.getPresidente());

        if (squadra != null) {
            // Recupera solo i giocatori tesserati attualmente
            Iterable<GiocatoreTesserato> giocatoriTesseratiAttivi = giocatoreTesseratoService.findTesseratiAttiviBySquadra(squadra);
            // Recupera i giocatori che non sono attualmente tesserati
            Iterable<Giocatore> giocatoriNonTesserati = giocatoreService.findNonTesserati();
            model.addAttribute("squadra", squadra);
            model.addAttribute("giocatoriTesseratiAttivi", giocatoriTesseratiAttivi);
            model.addAttribute("giocatoriNonTesserati", giocatoriNonTesserati);
            model.addAttribute("noSquadra", false);
        } else {
            model.addAttribute("noSquadra", true);
        }

        return "gestisciSquadra";
    }
    @GetMapping("/giocatori")
    public String viewAllGiocatori(Model model) {
        Iterable<Giocatore> giocatori = giocatoreService.findAll();
        model.addAttribute("giocatori", giocatori);
        return "viewAllGiocatori";
    }
    @PostMapping("/rimuoviGiocatore")
    public String rimuoviGiocatore(@RequestParam Long giocatoreTesseratoId, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        Squadra squadra = squadraService.findByPresidente(credentials.getPresidente());

        // Trova il tesseramento del giocatore
        GiocatoreTesserato giocatoreTesserato = giocatoreTesseratoService.findById(giocatoreTesseratoId);

        // Imposta la data di fine tesseramento
        if (giocatoreTesserato != null && giocatoreTesserato.getSquadra().equals(squadra)) {
            giocatoreTesserato.setDataFineTesseramento(LocalDate.now());
            giocatoreTesseratoService.save(giocatoreTesserato);
        }

        return "redirect:/gestisciSquadra";
    }
    @PostMapping("/tesseraGiocatore")
    public String tesseraGiocatore(@RequestParam Long giocatoreId, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        Squadra squadra = squadraService.findByPresidente(credentials.getPresidente());

        Giocatore giocatore = giocatoreService.findById(giocatoreId);
        GiocatoreTesserato giocatoreTesserato = new GiocatoreTesserato();
        giocatoreTesserato.setGiocatore(giocatore);
        giocatoreTesserato.setSquadra(squadra);
        giocatoreTesserato.setDataInizioTesseramento(LocalDate.now());

        // Aggiungi eventuale logica se necessario
        if (giocatoreTesserato.getDataFineTesseramento() == null) {
            // Il tesseramento è attivo, quindi non fare nulla con la data di fine tesseramento
        }

        giocatoreTesseratoService.save(giocatoreTesserato);

        return "redirect:/gestisciSquadra";
    }
    @GetMapping("/admin/nuovoGiocatore")
    public String showGiocatoreForm(Model model) {
        model.addAttribute("giocatore", new Giocatore());
        return "admin/nuovoGiocatore";
    }

    @PostMapping("/admin/nuovoGiocatore")
    public String saveGiocatore(@ModelAttribute("giocatore") Giocatore giocatore,
                                BindingResult bindingResult,
                                Model model) {

        // Valida il giocatore con il validator
        giocatoreValidator.validate(giocatore, bindingResult);

        // Se ci sono errori di validazione, ritorna il form con gli errori
        if (bindingResult.hasErrors()) {
            return "admin/nuovoGiocatore";  // Ritorna la vista del form
        }

        // Se non ci sono errori, salva il giocatore
        giocatoreService.saveGiocatore(giocatore);
        return "redirect:/admin/viewAllGiocatori";
    }

    @GetMapping("/admin/viewAllGiocatori")
    public String viewAllGiocatoriAdmin(Model model) {
        Iterable<Giocatore> giocatori = giocatoreService.findAll();
        model.addAttribute("giocatori", giocatori);
        return "admin/viewAllGiocatori";
    }


}
