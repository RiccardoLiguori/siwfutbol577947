package it.uniroma3.siwprogetto.repository;


import it.uniroma3.siwprogetto.model.Giocatore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface GiocatoreRepository extends CrudRepository<Giocatore,Long> {

    @Query("SELECT g FROM Giocatore g WHERE g.id NOT IN (SELECT gt.giocatore.id FROM GiocatoreTesserato gt WHERE gt.dataFineTesseramento IS NULL)")
    List<Giocatore> findNonTesserati();

    List<Giocatore> findByRuolo(String ruolo);
}
