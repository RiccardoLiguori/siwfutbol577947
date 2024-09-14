package it.uniroma3.siwprogetto.service;

import it.uniroma3.siwprogetto.model.Presidente;
import it.uniroma3.siwprogetto.model.Squadra;
import it.uniroma3.siwprogetto.repository.SquadraRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SquadraService {

    @Autowired
    protected SquadraRepository squadraRepository;

    public Squadra saveSquadra(Squadra squadra) {
        return this.squadraRepository.save(squadra);
    }

    public Iterable<Squadra> findAll() {
        return this.squadraRepository.findAll();
    }

    public Squadra findByPresidente(Presidente presidente) {
        return squadraRepository.findByPresidente(presidente);
    }

    public Squadra findById(Long id) {
        return this.squadraRepository.findById(id).orElse(null);
    }

    public Squadra findByName(String nome) { return this.squadraRepository.findByNome(nome);

    }
}
