package it.uniroma3.siwprogetto.service;

import it.uniroma3.siwprogetto.model.Presidente;
import it.uniroma3.siwprogetto.repository.PresidenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The UserService handles logic for Users.
 */
@Service
public class PresidenteService {

    @Autowired
    protected PresidenteRepository presidenteRepository;

    /**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
    @Transactional
    public Presidente getPresidente(Long id) {
        Optional<Presidente> result = this.presidenteRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method saves a User in the DB.
     * @param user the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     */

    public Presidente savePresidente(Presidente user) {
        return this.presidenteRepository.save(user);
    }

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */

    public List<Presidente> getAllUsers() {
        List<Presidente> result = new ArrayList<>();
        Iterable<Presidente> iterable = this.presidenteRepository.findAll();
        for(Presidente user : iterable)
            result.add(user);
        return result;
    }


    public Iterable<Presidente> findAll() {
        return this.presidenteRepository.findAll();
    }

    public Iterable<Presidente> findPresidentiSenzaSquadra() {
        return this.presidenteRepository.findPresidentiSenzaSquadra();
    }

    public Presidente findById(Long id) {
        return this.presidenteRepository.findById(id).orElse(null);
    }
}
