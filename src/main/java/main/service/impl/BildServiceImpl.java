package main.service.impl;

import main.service.BildService;
import main.domain.Bild;
import main.repository.BildRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Bild.
 */
@Service
@Transactional
public class BildServiceImpl implements BildService {

    private final Logger log = LoggerFactory.getLogger(BildServiceImpl.class);

    private final BildRepository bildRepository;

    public BildServiceImpl(BildRepository bildRepository) {
        this.bildRepository = bildRepository;
    }

    /**
     * Save a bild.
     *
     * @param bild the entity to save
     * @return the persisted entity
     */
    @Override
    public Bild save(Bild bild) {
        log.debug("Request to save Bild : {}", bild);
        return bildRepository.save(bild);
    }

    /**
     * Get all the bilds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Bild> findAll(Pageable pageable) {
        log.debug("Request to get all Bilds");
        return bildRepository.findAll(pageable);
    }


    /**
     * Get one bild by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Bild> findOne(Long id) {
        log.debug("Request to get Bild : {}", id);
        return bildRepository.findById(id);
    }

    /**
     * Delete the bild by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bild : {}", id);
        bildRepository.deleteById(id);
    }
}
