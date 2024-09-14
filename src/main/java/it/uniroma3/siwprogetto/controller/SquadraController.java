package it.uniroma3.siwprogetto.controller;

import it.uniroma3.siwprogetto.model.*;
import it.uniroma3.siwprogetto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class SquadraController {

    @Autowired
    private SquadraService squadraService;

    @GetMapping("/squadre")
    public String showSquadre(Model model) {
        Iterable<Squadra> squadre = squadraService.findAll();
        model.addAttribute("squadre", squadre);
        return "squadre";
    }

    @GetMapping("/squadra/{id}")
    public String mostraDettagliSquadra(@PathVariable("id") Long id, Model model) {
        Squadra squadra = squadraService.findById(id);

        // Filtra i giocatori tesserati per quelli con tesseramento attivo
        List<GiocatoreTesserato> giocatoriAttivi = squadra.getGiocatoriTesserati().stream()
                .filter(giocatoreTesserato -> giocatoreTesserato.getDataFineTesseramento() == null ||
                        giocatoreTesserato.getDataFineTesseramento().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        model.addAttribute("squadra", squadra);
        model.addAttribute("giocatori", giocatoriAttivi);  // Passa solo i giocatori con tesseramento attivo
        model.addAttribute("presidente", squadra.getPresidente());

        return "dettagliSquadra";
    }


}
