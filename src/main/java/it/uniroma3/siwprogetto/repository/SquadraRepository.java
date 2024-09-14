package it.uniroma3.siwprogetto.repository;


import it.uniroma3.siwprogetto.model.Presidente;
import it.uniroma3.siwprogetto.model.Squadra;
import org.springframework.data.repository.CrudRepository;




public interface SquadraRepository extends CrudRepository<Squadra,Long> {

    Squadra findByPresidente(Presidente presidente);

    Squadra findByNome(String nome);
}
