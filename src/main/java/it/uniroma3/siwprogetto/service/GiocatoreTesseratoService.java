package it.uniroma3.siwprogetto.service;

import it.uniroma3.siwprogetto.model.GiocatoreTesserato;
import it.uniroma3.siwprogetto.model.Squadra;
import it.uniroma3.siwprogetto.repository.GiocatoreTesseratoRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiocatoreTesseratoService {

    @Autowired
    GiocatoreTesseratoRepository giocatoreTesseratoRepository;

    public void save(GiocatoreTesserato giocatoreTesserato) {
        giocatoreTesseratoRepository.save(giocatoreTesserato);
    }

    public GiocatoreTesserato findById(Long giocatoreTesseratoId) {
        return this.giocatoreTesseratoRepository.findById(giocatoreTesseratoId).orElse(null);
    }

    public Iterable<GiocatoreTesserato> findTesseratiAttiviBySquadra(Squadra squadra) {
        return giocatoreTesseratoRepository.findBySquadraAndDataFineTesseramentoIsNull(squadra);
    }
}
