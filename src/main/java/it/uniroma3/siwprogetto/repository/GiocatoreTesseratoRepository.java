package it.uniroma3.siwprogetto.repository;

import it.uniroma3.siwprogetto.model.GiocatoreTesserato;
import it.uniroma3.siwprogetto.model.Squadra;
import org.springframework.data.repository.CrudRepository;


public interface GiocatoreTesseratoRepository extends CrudRepository<GiocatoreTesserato,Long> {

    Iterable<GiocatoreTesserato> findBySquadraAndDataFineTesseramentoIsNull(Squadra squadra);
}
