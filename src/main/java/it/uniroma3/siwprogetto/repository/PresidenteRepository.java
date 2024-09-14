package it.uniroma3.siwprogetto.repository;


import java.util.Optional;

import it.uniroma3.siwprogetto.model.Presidente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PresidenteRepository extends CrudRepository<Presidente,Long> {

    // Recupera solo i presidenti che non sono amministratori e che non hanno una squadra assegnata
   @Query("SELECT p FROM Presidente p WHERE p.isAdmin = false AND p.squadra IS NULL")
    Iterable<Presidente> findPresidentiSenzaSquadra();

}
