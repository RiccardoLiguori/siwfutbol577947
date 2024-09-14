package it.uniroma3.siwprogetto.controller;

import it.uniroma3.siwprogetto.config.FileUploadUtil;
import it.uniroma3.siwprogetto.model.Presidente;
import it.uniroma3.siwprogetto.model.Squadra;
import it.uniroma3.siwprogetto.service.*;
import it.uniroma3.siwprogetto.validator.SquadraValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PresidenteController {

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
    private SquadraValidator squadraValidator;

    private static final String UPLOAD_DIR = "C:/Users/ricky/Desktop/UNIVERSITA RICCARDO/SIW/siwfutbol";

    @GetMapping("/admin/createSquadra")
    public String showCreateSquadraForm(Model model) {

        Iterable<Presidente> presidentiSenzaSquadra = presidenteService.findPresidentiSenzaSquadra();
        model.addAttribute("squadra", new Squadra());
        model.addAttribute("presidenti", presidentiSenzaSquadra);
        return "admin/createSquadra";
    }

    @PostMapping("/admin/createSquadra")
    public String createSquadra(@ModelAttribute("squadra") Squadra squadra,
                                BindingResult squadraBindingResult,
                                @RequestParam("immagine") MultipartFile file,
                                Model model) {

        // Valida la squadra
        squadraValidator.validate(squadra, squadraBindingResult);

        // Verifica che l'immagine sia stata caricata
        if (file == null || file.isEmpty()) {
            squadraBindingResult.rejectValue("imagePath", "error.squadra", "L'immagine è obbligatoria.");
        }

        // Se ci sono errori, ritorna alla pagina del form con gli errori e ripopola la lista dei presidenti
        if (squadraBindingResult.hasErrors()) {
            model.addAttribute("presidenti", presidenteService.findPresidentiSenzaSquadra()); // Ripopola la lista dei presidenti
            return "admin/createSquadra";
        }

        try {
            // Carica l'immagine se è presente
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // Salva il percorso relativo per l'immagine
            squadra.setImagePath("/uploads/" + fileName);

            // Salva la squadra con l'immagine
            squadraService.saveSquadra(squadra);

        } catch (IOException e) {
            e.printStackTrace();
            // Gestione dell'errore di caricamento dell'immagine
            squadraBindingResult.rejectValue("immagine", "error.squadra", "Errore nel caricamento dell'immagine.");
            model.addAttribute("presidenti", presidenteService.findPresidentiSenzaSquadra()); // Ripopola la lista dei presidenti
            return "admin/createSquadra";
        }

        // Reindirizza alla pagina di visualizzazione delle squadre
        return "redirect:/admin/viewSquadra";
    }


    @GetMapping("/admin/viewSquadra")
    public String showSquadre(Model model) {
        Iterable<Squadra> squadre = squadraService.findAll();
        model.addAttribute("squadre", squadre);
        return "admin/squadre";
    }


    @GetMapping("/admin/updateSquadra/{id}")
    public String showUpdateSquadraForm(@PathVariable("id") Long id, Model model) {
        Squadra squadra = squadraService.findById(id);
        Iterable<Presidente> presidentiSenzaSquadra = presidenteService.findPresidentiSenzaSquadra();

        List<Presidente> presidentiDisponibili = new ArrayList<>();
        for (Presidente presidente : presidentiSenzaSquadra) {
            presidentiDisponibili.add(presidente);
        }
        // Aggiungi il presidente attuale alla lista con un'etichetta speciale
        if (squadra.getPresidente() != null) {
            Presidente presidenteAttuale = squadra.getPresidente();
            model.addAttribute("presidenteAttuale", presidenteAttuale);
            presidentiDisponibili.add(presidenteAttuale);
        }

        model.addAttribute("squadra", squadra);
        model.addAttribute("presidentiDisponibili", presidentiDisponibili);

        return "admin/updateSquadra";
    }

    @PostMapping("/admin/updateSquadra")
    public String updateSquadra(@ModelAttribute("squadra") Squadra squadra,
                                @RequestParam("immagine") MultipartFile file) {
        // Trova la squadra esistente
        Squadra squadraEsistente = squadraService.findById(squadra.getId());

        // Se la squadra aveva già un presidente, rendilo disponibile
        if (squadraEsistente.getPresidente() != null && !squadraEsistente.getPresidente().getId().equals(squadra.getPresidente().getId())) {
            Presidente vecchioPresidente = squadraEsistente.getPresidente();
            vecchioPresidente.setSquadra(null); // Rimuove la squadra dal vecchio presidente
            presidenteService.savePresidente(vecchioPresidente); // Salva il vecchio presidente come non associato
        }

        // Assegna il nuovo presidente alla squadra
        Presidente nuovoPresidente = presidenteService.findById(squadra.getPresidente().getId());
        squadraEsistente.setPresidente(nuovoPresidente);

        // Gestione dell'upload della foto
        if (!file.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());

                // Salva il percorso relativo per il browser
                squadraEsistente.setImagePath("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                // Gestione dell'errore - Puoi mostrare un messaggio di errore all'utente
            }
        }

        // Salva la squadra aggiornata
        squadraService.saveSquadra(squadraEsistente);

        return "redirect:/admin/viewSquadra";
    }


}