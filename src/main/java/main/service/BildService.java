package main.service;

import main.domain.Bild;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Bild.
 */
public interface BildService {

    /**
     * Save a bild.
     *
     * @param bild the entity to save
     * @return the persisted entity
     */
    Bild save(Bild bild);

    /**
     * Get all the bilds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Bild> findAll(Pageable pageable);


    /**
     * Get the "id" bild.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Bild> findOne(Long id);

    /**
     * Delete the "id" bild.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
