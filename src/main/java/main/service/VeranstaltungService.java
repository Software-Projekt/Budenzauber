package main.service;

import main.domain.Veranstaltung;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Veranstaltung.
 */
public interface VeranstaltungService {

    /**
     * Save a veranstaltung.
     *
     * @param veranstaltung the entity to save
     * @return the persisted entity
     */
    Veranstaltung save(Veranstaltung veranstaltung);

    /**
     * Get all the veranstaltungs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Veranstaltung> findAll(Pageable pageable);


    /**
     * Get the "id" veranstaltung.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Veranstaltung> findOne(Long id);

    /**
     * Delete the "id" veranstaltung.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
