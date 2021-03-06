package main.service;

import main.domain.Photo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Interface for managing Photo.
 */
public interface PhotoService {

    /**
     * Save a photo.
     *
     * @param photo the entity to save
     * @return the persisted entity
     */
    Photo save(Photo photo);

    /**
     * Get all the photos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Photo> findAll(Pageable pageable);

    /**
     * Get all the Photo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Photo> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" photo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Photo> findOne(Long id);

    /**
     * Delete the "id" photo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get all photos for event.
     *
     * @param pageable the pagination information
     * @param id the event id
     * @return the list of entities
     */
    Page<Photo> findPhotosByEvent(Pageable pageable, long id);
}
