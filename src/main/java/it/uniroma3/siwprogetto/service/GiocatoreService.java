package it.uniroma3.siwprogetto.service;

import it.uniroma3.siwprogetto.model.Giocatore;
import it.uniroma3.siwprogetto.repository.GiocatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiocatoreService {

    @Autowired
    private GiocatoreRepository giocatoreRepository;

    public List<Giocatore> findNonTesserati() {
        return giocatoreRepository.findNonTesserati();
    }

    public Giocatore findById(Long id) {
        return giocatoreRepository.findById(id).orElse(null);
    }

    public Iterable<Giocatore> findAll() {
        return this.giocatoreRepository.findAll();
    }

    public Iterable<Giocatore> findGiocatoriByRuolo(String ruolo) {
        return giocatoreRepository.findByRuolo(ruolo);
    }

    public void saveGiocatore(Giocatore giocatore) {
        giocatoreRepository.save(giocatore);
    }

}